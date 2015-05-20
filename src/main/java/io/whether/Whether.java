package io.whether;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;

class Whether {

    public static void main(String[] args) {
        try {
            Configuration config = new PropertiesConfiguration(args[0]);

            File stationDataFile = new File((String) config.getProperty("gsodFolderPath") + File.separatorChar + "isd-history.txt");
            StationDataParser parser = new StationDataParser(stationDataFile);
            parser.parse();
        } catch (ConfigurationException ce) {
            //TODO log error
        }
    }
}
