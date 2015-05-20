package io.whether;

import io.whether.domain.Station;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StationDataParser {
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    List<Station> stations;
    File stationDataFile;

    public StationDataParser(File inFile) {
        stationDataFile = inFile;
        stations = new ArrayList<>();
    }

    public boolean parse() {
        try {
            BufferedReader rdr = new BufferedReader(new FileReader(stationDataFile));

            //read through until
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
                String periodBegin = line.substring(82, 91).trim();
                String periodEnd = line.substring(91).trim();

                //data will be reported geographically, no need to include stations that are missing this info
                if (latStr.length() == 0 || longStr.length() == 0 || elevStr.length() == 0) continue;

                int usafID = Integer.parseInt(usafStr);
                int wbanID = Integer.parseInt(wbanStr);

                float latitude = Float.parseFloat(latStr);
                float longitude = Float.parseFloat(longStr);
                float elevation = Float.parseFloat(elevStr);

                LocalDate periodBeginDate = LocalDate.parse(periodBegin, dateFormatter);
                LocalDate periodEndDate = LocalDate.parse(periodEnd, dateFormatter);

                Station currentStation = new Station(usafID, wbanID, latitude, longitude, elevation, periodBeginDate, periodEndDate);

                currentStation.setStationName(stationName);
                currentStation.setCountryID(fips);
                currentStation.setState(state);
                currentStation.setIcaoID(icao);

                System.out.println(currentStation.toString());
            }


        } catch (IOException ioe) {
            //TODO add logging
        }
        return true;
    }

    public List<Station> getStations() {
        return stations;
    }
}
