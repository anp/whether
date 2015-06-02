package io.whether;

import io.whether.database.DatabasePersister;
import io.whether.domain.Station;
import io.whether.parsing.GSODParser;
import io.whether.parsing.StationDataParser;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGPoolingDataSource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.exceptions.DBIException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Whether {

	private static List<Station> stations;
	private static Logger log = LogManager.getLogger(Whether.class);

	private static int filesProcessed = 0;
	private static int numGsodFiles = 0;

	public static void main(String[] args) {

		try {
			Configuration config = new PropertiesConfiguration(args[0]);

			PGPoolingDataSource ds = new PGPoolingDataSource();
			ds.setServerName(config.getString("postgresAddress", "localhost"));
			ds.setPortNumber(Integer.parseInt(config.getString("postgresPort", "5432")));
			ds.setDatabaseName(config.getString("postgresDBName", "whether"));
			ds.setUser(config.getString("postgresUser", "postgres"));
			ds.setPassword(config.getString("postgresPass"));
			ds.setMaxConnections(Runtime.getRuntime().availableProcessors());

			DBI dbi = new DBI(ds);

			String gsodPath = config.getString("gsodFolderPath");

			String inventoryFilename = config.getString("stationInventoryFilename");

			try {
				DatabasePersister dao = dbi.open(DatabasePersister.class);

				try {
					log.info("Creating tables...");
					dao.createStationTable();
					dao.createSummaryTable();
					log.info("Schema successfully initialized.");
				} catch (DBIException dbie) {
					log.warn("Problem initializing tables and indices. Database is probably not a clean install.");
				}

				File stationDataFile = new File(gsodPath + File.separatorChar + inventoryFilename);
				StationDataParser parser = new StationDataParser(stationDataFile);
				parser.parse();

				stations = parser.getStations();
				log.info("Station data parsed for " + stations.size() + " stations. Inserting station data into database...");

				parser.getStations().forEach(s -> {
					try {
						dao.insertStationRecord(
								s.getStationID(),
								s.getWbanID(),
								s.getStationName(),
								s.getCountryID(),
								s.getState(),
								s.getLatitude(),
								s.getLongitude(),
								s.getElevation());
					} catch (DBIException dbie) {
						log.error("Unable to persist station " + s.getStationName() + ". " + dbie.getMessage());
					}
				});
				log.info("Station data successfully persisted to database.");
				dao.close();

				String unpackedFolderName = config.getString("unpackedFolderPath");
				File gsodUnpackedFolder = new File(gsodPath + File.separatorChar + unpackedFolderName);

				File[] opFiles = gsodUnpackedFolder.listFiles();
				if (opFiles == null || opFiles.length == 0) {
					throw new IOException("Configured GSOD unzipped directory doesn't exist or is empty. " +
							"Make sure to run this from the main script unless you know what you're doing!");
				}

				List<File> files = Arrays.stream(opFiles).filter(f -> f.getName().endsWith(".op")).collect(Collectors.toList());

				log.info(files.size() + " GSOD files found.");
				numGsodFiles = files.size();
				ExecutorService execService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

				for (File f : files) {
					execService.submit(() -> {
						DatabasePersister persister = dbi.open(DatabasePersister.class);
						GSODParser currParser = new GSODParser(f);
						currParser.parse();
						persister.insertSummaryRecord(currParser.getSummaries());
						persister.close();
						tickParsingStatus();
					});
				}

				try {
					execService.shutdown();
					execService.awaitTermination(6, TimeUnit.HOURS);
				} catch (InterruptedException ie) {
					log.error(ie);
				}

				try {
					log.info("Parsed all files. Creating summary index...");
					dao.createSummaryIndex();
					log.info("Successfully indexed all daily summaries.");
				} catch (DBIException dbie) {
					log.error("Problem creating summary index. " + dbie.getMessage());
				}

			} catch (IOException ioe) {
				log.error(ioe);
			}

		} catch (ConfigurationException ce) {
			log.error("Problem reading configuration file " + args[0], ce);
		} catch (DBIException dbie) {
			log.error("Problem persisting data to DB.", dbie);
		}


	}

	public static Optional<Station> getStation(int stationNumber, int wbanID) {
		Optional<Station> returned = Optional.empty();

		for (Station s : stations) {
			if (s.getStationID() == stationNumber && s.getWbanID() == wbanID) {
				returned = Optional.of(s);
				break;
			}
		}

		return returned;
	}

	private synchronized static void tickParsingStatus() {
		filesProcessed++;
		if ((filesProcessed % 5000) == 0) {
			double percentDone = filesProcessed * 100.0 / numGsodFiles;

			log.debug(((int) percentDone) + "% of GSOD files parsed, "
					+ filesProcessed + "/" + numGsodFiles + " files completed.");
		}
	}
}
