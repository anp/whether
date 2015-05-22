package io.whether;

import io.whether.domain.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StationDataParser {

    private static final Logger log = LogManager.getLogger(StationDataParser.class);

    List<Station> stations;
    File stationDataFile;

    public StationDataParser(File inFile) {
        stationDataFile = inFile;
        stations = new CopyOnWriteArrayList<>();
    }

    public boolean parse() {
        try {
            BufferedReader rdr = new BufferedReader(new FileReader(stationDataFile));

            //read through until we're past the file header
            while (!rdr.readLine().startsWith("USAF")) ;

            String line = rdr.readLine();

            while ((line = rdr.readLine()) != null) {
                String usafStr = line.substring(0, 7).trim();
                String wbanStr = line.substring(7, 13).trim();
                String stationName = line.substring(13, 43).trim();
                String fips = line.substring(43, 48).trim();
                String state = line.substring(48, 51).trim();
                String icao = line.substring(51, 57).trim();
                String latStr = line.substring(57, 65).trim();
                String longStr = line.substring(65, 74).trim();
                String elevStr = line.substring(74, 82).trim();

                try {
                    int usafID = Integer.parseInt(usafStr);
                    int wbanID = Integer.parseInt(wbanStr);

                    float latitude = Float.parseFloat(latStr);
                    float longitude = Float.parseFloat(longStr);
                    float elevation = Float.parseFloat(elevStr);

                    Station currentStation = new Station(usafID, wbanID, latitude, longitude, elevation);

                    currentStation.setStationName(stationName);
                    currentStation.setCountryID(fips);
                    currentStation.setState(state);
                    currentStation.setIcaoID(icao);

                    stations.add(currentStation);
                } catch (NumberFormatException nfe) {
                    //we want to ignore any stations that have letters in their IDs -- the GSOD data doesn't cover them
                }

            }


        } catch (IOException ioe) {
            log.error("IO error when reading station data file " + stationDataFile.getAbsolutePath(), ioe);
            return false;
        }
        return true;
    }

    public List<Station> getStations() {
        return stations;
    }
}
