package io.whether.parsing;

import io.whether.Whether;
import io.whether.domain.DailySummary;
import io.whether.domain.PrecipitationReportsFlags;
import io.whether.domain.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GSODParser {
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final Logger log = LogManager.getLogger(GSODParser.class);

	File gsodFile;
	List<DailySummary> summaries;

	Station previousStation;

	boolean mismatchedIDsFound = false;

	public GSODParser(File inFile) {
		gsodFile = inFile;
		summaries = new ArrayList<>();
	}

	public boolean parse() {
		try {
			BufferedReader rdr = new BufferedReader(new FileReader(gsodFile));

			String line = rdr.readLine();

			while ((line = rdr.readLine()) != null) {
				DailySummary summary = parseDailySummary(line);
				if (summary != null) {
					summaries.add(summary);
				}
			}

			rdr.close();
		} catch (IOException ioe) {
			log.error("IO Error when reading GSOD file " + gsodFile.getAbsolutePath(), ioe);
			return false;
		}
		return true;
	}

	private DailySummary parseDailySummary(String line) {
		Station station;
		String statNumStr = line.substring(0, 6).trim();
		String wbanStr = line.substring(7, 12).trim();

		int stationNumber = Integer.parseInt(statNumStr);
		int wbanNumber = Integer.parseInt(wbanStr);

		Optional<Station> stationOptional = Whether.getStation(stationNumber, wbanNumber);
		if (stationOptional.isPresent()) {


			station = stationOptional.get();
			if (previousStation != null && !previousStation.equals(station)) {
				log.warn("Station data changes mid-file!\n" + previousStation + "\n" + station);
			}
			previousStation = station;
		} else {
			log.trace("No station data found for station number " + stationNumber + ", wban number " + wbanNumber);
			return null;
		}


		//bootstrap the summary object
		String dateStr = line.substring(14, 22).trim();

		LocalDate date = LocalDate.parse(dateStr, dateFormatter);

		if ((stationNumber != station.getStationID() || wbanNumber != station.getWbanID()) && !mismatchedIDsFound) {
			GSODParser.log.warn("STATION DATA MISMATCH: found station number " + stationNumber + "\t, found wban number " + wbanNumber + "\t in " + date + ", previously found station:\n\t" + station);
			mismatchedIDsFound = true;
		}

		DailySummary summary = new DailySummary(station, date);

        /*now we need to go through each field and nullify any missing values per the NCDC GSOD spec

          Each block has this structure:

          1. Grab the relevant strings from the read line.
          2. Parse the substrings into relevant number types.
          3. Check the parsed numbers against the NCDC values that indicate missing data.
          4. Nullify any missing data points.
          5. Set those values to the summary that we're currently parsing.

          For format information see:
          http://www1.ncdc.noaa.gov/pub/data/gsod/readme.txt
        */

		//mean temperature
		String meanTempStr = line.substring(24, 30).trim();
		String meanTempCountStr = line.substring(31, 33).trim();

		Double meanTemperature = Double.parseDouble(meanTempStr);
		int meanTempCount = Integer.parseInt(meanTempCountStr);

		if (Math.abs(meanTemperature - 9999.9) < 0.01) meanTemperature = null;
		summary.setMeanTemperatureFahrenheit(meanTemperature, meanTempCount);

		//dew point
		String dewPointStr = line.substring(35, 41).trim();
		String dewPointCountStr = line.substring(42, 44).trim();

		Double dewPoint = Double.parseDouble(dewPointStr);
		int dewPointCount = Integer.parseInt(dewPointCountStr);

		if (Math.abs(dewPoint - 9999.9) < 0.01) dewPoint = null;
		summary.setDewPointFahrenheit(dewPoint, dewPointCount);

		//sea-level pressure
		String seaPressureStr = line.substring(46, 52).trim();
		String seaPressureCountStr = line.substring(53, 55).trim();

		Double seaPressure = Double.parseDouble(seaPressureStr);
		int seaPressureCount = Integer.parseInt(seaPressureCountStr);

		if (Math.abs(seaPressure - 9999.9) < 0.01) seaPressure = null;
		summary.setSeaLevelPressureMillibars(seaPressure, seaPressureCount);

		//station-level pressure
		String statPressureStr = line.substring(57, 63).trim();
		String statPressureCountStr = line.substring(64, 66).trim();

		Double stationPressure = Double.parseDouble(statPressureStr);
		int stationPressureCount = Integer.parseInt(statPressureCountStr);

		if (Math.abs(stationPressure - 9999.9) < 0.01) stationPressure = null;
		summary.setStationPressureMillibars(stationPressure, stationPressureCount);

		//miles of visibility
		String visibilityStr = line.substring(68, 73).trim();
		String visibilityCountStr = line.substring(74, 76).trim();

		Double visibility = Double.parseDouble(visibilityStr);
		int visibilityCount = Integer.parseInt(visibilityCountStr);

		if (Math.abs(visibility - 999.9) < 0.01) visibility = null;
		summary.setVisibilityMiles(visibility, visibilityCount);

		//mean wind speed for the day
		String meanWindSpdStr = line.substring(78, 83).trim();
		String meanWindSpdCountStr = line.substring(84, 86).trim();

		Double meanWindSpeed = Double.parseDouble(meanWindSpdStr);
		int meanWindSpeedCount = Integer.parseInt(meanWindSpdCountStr);

		if (Math.abs(meanWindSpeed - 999.9) < 0.01) meanWindSpeed = null;
		summary.setMeanWindSpeedKnots(meanWindSpeed, meanWindSpeedCount);

		//max wind speed for the day
		String maxWindSpdStr = line.substring(88, 93).trim();

		Double maxWindSpeed = Double.parseDouble(maxWindSpdStr);

		if (Math.abs(maxWindSpeed - 9999.9) < 0.01) maxWindSpeed = null;

		summary.setMaxWindSpeedKnots(maxWindSpeed);

		//max wind gust for the day
		String maxWindGustStr = line.substring(95, 100).trim();

		Double maxWindGust = Double.parseDouble(maxWindGustStr);

		if (Math.abs(maxWindGust - 999.9) < 0.01) maxWindGust = null;

		summary.setMaxWindGustKnots(maxWindGust);

		//max temperature for the day
		String maxTempStr = line.substring(102, 108).trim();
		String maxTempFlagStr = line.substring(108, 109).trim();

		Double maxTemp = Double.parseDouble(maxTempStr);
		boolean maxTempIsFromHourlyData = maxTempFlagStr.equals("*");

		if (Math.abs(maxTemp - 9999.9) < 0.01) maxTemp = null;

		summary.setMaxTemperatureFahrenheit(maxTemp, maxTempIsFromHourlyData);

		//min temperature for the day
		String minTempStr = line.substring(110, 116).trim();
		String minTempFlagStr = line.substring(116, 117).trim();

		Double minTemp = Double.parseDouble(minTempStr);
		boolean minTempIsFromHourlyData = minTempFlagStr.equals("*");

		if (Math.abs(minTemp - 9999.9) < 0.01) minTemp = null;

		summary.setMinTemperatureFahrenheit(minTemp, minTempIsFromHourlyData);

		//precipitation in inches for the day
		String precipStr = line.substring(118, 123).trim();
		char precipFlag = line.charAt(123);

		Double precipitation = Double.parseDouble(precipStr);

		if (Math.abs(precipitation - 99.99) < 0.01
				&& precipFlag != PrecipitationReportsFlags.HOURLY_PRECIP_BUT_NO_DAILY_PRECIP)
			precipitation = 0.0;

		summary.setPrecipitationInches(precipitation, precipFlag + "");

		//snow depth for the day
		String snowDepthStr = line.substring(125, 130).trim();

		Double snowDepth = Double.parseDouble(snowDepthStr);

		if (Math.abs(snowDepth - 999.9) < 0.01) snowDepth = 0.0;

		summary.setSnowDepth(snowDepth);

		//array of boolean flags relating to weather events
		char[] weatherEventFlags = line.substring(132, 138).trim().toCharArray();

		if (weatherEventFlags.length != 6)
			throw new RuntimeException("Error parsing weather event flags -- incorrect length.");

		summary.setFogReported(weatherEventFlags[0] == '1');
		summary.setRainOrDrizzleReported(weatherEventFlags[1] == '1');
		summary.setSnowOrIcePelletsReported(weatherEventFlags[2] == '1');
		summary.setHailReported(weatherEventFlags[3] == '1');
		summary.setThunderReported(weatherEventFlags[4] == '1');
		summary.setTornadoReported(weatherEventFlags[5] == '1');
		return summary;
	}

	public List<DailySummary> getSummaries() {
		return summaries;
	}
}
