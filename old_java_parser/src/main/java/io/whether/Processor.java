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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Processor {

	private static final int N_THREADS = Runtime.getRuntime().availableProcessors();
	private static Map<String, Station> stationLookup;
	private static Logger log = LogManager.getLogger(Processor.class);

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
			ds.setMaxConnections(N_THREADS);

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

				stationLookup = new HashMap<>();

				for (Station station : parser.getStations()) {
					stationLookup.put(station.getStationID() + "|" + station.getWbanID(), station);
				}

				stationLookup.putAll(parser.getDuplicateReplacements());

				log.info("Station data parsed for " + stationLookup.size() + " stations. Inserting station data into database...");

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
				ExecutorService execService = Executors.newFixedThreadPool(N_THREADS);

				int partitionSize = files.size() / N_THREADS;
				for (int n = 0; n < N_THREADS; n++) {
					int startIndex = partitionSize * n;

					int calculatedEndIndex = partitionSize * (n + 1);
					int endIndex = ((numGsodFiles - calculatedEndIndex) < partitionSize
							|| calculatedEndIndex > numGsodFiles)
							? numGsodFiles : calculatedEndIndex;

					log.info("Sublist boundaries for worker thread " + n + ": " + startIndex + "-" + endIndex + " / " + numGsodFiles);

					execService.submit(() -> {
						DatabasePersister persister = dbi.open(DatabasePersister.class);
						for (File f : files.subList(startIndex, endIndex)) {

							GSODParser currParser = new GSODParser(f);
							currParser.parse();
							persister.insertSummaryRecord(currParser.getSummaries());
							tickParsingStatus();
						}
						persister.close();
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
					dao.cleanUp();
					log.info("Successfully compacted and analyzed database.");
				} catch (DBIException dbie) {
					log.error("Problem creating summary index. " + dbie.getMessage());
				}

				dao.close();
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

		String key = stationNumber + "|" + wbanID;
		if (stationLookup.containsKey(key)) {
			returned = Optional.of(stationLookup.get(key));
		}

		return returned;
	}

	private synchronized static void tickParsingStatus() {
		filesProcessed++;
		if ((filesProcessed % 40000) == 0) {
			double percentDone = filesProcessed * 100.0 / numGsodFiles;

			log.debug(((int) percentDone) + "% of GSOD files parsed, "
					+ filesProcessed + "/" + numGsodFiles + " files completed.");
		}
	}
}
