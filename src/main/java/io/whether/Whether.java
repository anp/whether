package io.whether;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;

class Whether {

    public static void main(String[] args) {
        try {
            Configuration config = new PropertiesConfiguration(args[0]);

            String gsodPath = (String) config.getProperty("gsodFolderPath");

            String inventoryFilename = (String) config.getProperty("stationInventoryFilename");
            File stationDataFile = new File(gsodPath + File.separatorChar + inventoryFilename);
            StationDataParser parser = new StationDataParser(stationDataFile);
            parser.parse();

            String unpackedFolderName = (String) config.getProperty("unpackedFolderPath");
            File gsodUnpackedFolder = new File(gsodPath + File.separatorChar + unpackedFolderName);

        } catch (ConfigurationException ce) {
            //TODO log error
        }
    }
}
