package io.whether;

import java.io.File;

class Whether {

    public static void main(String[] args) {
        File stationDataFile = new File(args[0]);
        StationDataParser parser = new StationDataParser(stationDataFile);
        parser.parse();
    }
}
