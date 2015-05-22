package io.whether;

import io.whether.domain.DailySummary;
import io.whether.domain.Station;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Whether {

    private static List<Station> stations;
    private static Logger log = LogManager.getLogger(Whether.class);

    public static void main(String[] args) {
        try {
            Configuration config = new PropertiesConfiguration(args[0]);

            String gsodPath = (String) config.getProperty("gsodFolderPath");

            String inventoryFilename = (String) config.getProperty("stationInventoryFilename");
            File stationDataFile = new File(gsodPath + File.separatorChar + inventoryFilename);
            StationDataParser parser = new StationDataParser(stationDataFile);
            parser.parse();

            stations = parser.getStations();

            String unpackedFolderName = (String) config.getProperty("unpackedFolderPath");
            File gsodUnpackedFolder = new File(gsodPath + File.separatorChar + unpackedFolderName);

            File[] opFiles = gsodUnpackedFolder.listFiles();
            if (opFiles == null || opFiles.length == 0) {
                throw new IOException("Configured GSOD unzipped directory doesn't exist or is empty. Make sure to run this from the main script unless you know what you're doing!");
            }

            List<File> files = Arrays.stream(opFiles).filter(f -> f.getName().endsWith(".op")).collect(Collectors.toList());

            log.info(files.size() + " GSOD files found.");
            int filesProcessed = 0;
            for (File f : files) {

                GSODParser currParser = new GSODParser(f);
                currParser.parse();

                List<DailySummary> summaries = currParser.getSummaries();

                filesProcessed++;
                if ((filesProcessed % 5000) == 0) {
                    double percentDone = ((double) filesProcessed) * 100.0 / files.size();

                    log.debug(((int) percentDone) + "% of GSOD files parsed, "
                            + filesProcessed + "/" + files.size() + " files completed.");
                }
            }

            log.info("Parsed all files.");

        } catch (ConfigurationException ce) {
            log.error("Problem reading configuration file " + args[0], ce);
        } catch (IOException ioe) {
            log.error(ioe);
        }
    }

    public static Optional<Station> getStation(int stationNumber, int wbanID) {
        Optional<Station> returned = Optional.empty();

        for (Station s : stations) {
            if (s.getUsafID() == stationNumber && s.getWbanID() == wbanID) {
                returned = Optional.of(s);
                break;
            }
        }

        return returned;
    }
}
