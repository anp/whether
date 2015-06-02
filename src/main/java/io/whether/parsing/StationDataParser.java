package io.whether.parsing;

import io.whether.domain.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
			String line = rdr.readLine();

			if (line == null) {
				throw new IOException("Station data file is empty or nonexistant.");
			}

			while (!line.startsWith("USAF")) {
				line = rdr.readLine();
			}
			rdr.readLine();

			while ((line = rdr.readLine()) != null) {
				String usafStr = line.substring(0, 7).trim();
				String wbanStr = line.substring(7, 13).trim();
				String stationName = line.substring(13, 43).trim();
				String country = line.substring(43, 48).trim();
				String state = line.substring(48, 51).trim();
				String latStr = line.substring(51, 59).trim();
				String longStr = line.substring(59, 68).trim();
				String elevStr = line.substring(68, 76).trim();

				try {
					if (stationName.equals("")) {
						stationName = usafStr + '/' + wbanStr;
					}

					int usafID = Integer.parseInt(usafStr);
					int wbanID = Integer.parseInt(wbanStr);

					Double latitude = null;
					Double longitude = null;
					try {
						latitude = Double.parseDouble(latStr);
						longitude = Double.parseDouble(longStr);
					} catch (NumberFormatException nfe) {
						log.trace("Unable to parse latitude/longitude for " + stationName);
					}

					Double elevation = null;
					try {
						elevation = Double.parseDouble(elevStr);
					} catch (NumberFormatException nfe) {
						log.trace("Unable to parse elevation for " + stationName);
					}

					Station currentStation = new Station(usafID, wbanID, latitude, longitude, elevation);

					currentStation.setStationName(stationName);
					currentStation.setCountryID(country);
					currentStation.setState(state);

					Optional<Station> dupeStation = getDuplicateStation(currentStation);

					if (dupeStation.isPresent()) {
						Station previousStation = dupeStation.get();
						mergeStations(previousStation, currentStation);
					} else {
						stations.add(currentStation);
					}
					log.trace(currentStation.toString());
				} catch (NumberFormatException nfe) {
					//we want to ignore any stations that have letters in their IDs -- the GSOD data doesn't cover them
					log.trace("Unable to parse integer IDs for " + usafStr + '/' + wbanStr + ". Skipping as they aren't included in GSOD data.");
				}
			}

			rdr.close();
		} catch (IOException ioe) {
			log.error("IO error when reading station data file " + stationDataFile.getAbsolutePath(), ioe);
			return false;
		}

		return true;
	}

	private Optional<Station> getDuplicateStation(Station newStation) {
		Optional<Station> returned = Optional.empty();

		for (Station other : stations) {
			if (newStation.getStationID() == other.getStationID()
					&& newStation.getWbanID() == other.getWbanID()) {
				returned = Optional.of(other);
				break;
			}
		}

		return returned;
	}

	private void mergeStations(Station master, Station dupe) {
		if (master.getStationName() == null && dupe.getStationName() != null) {
			master.setStationName(dupe.getStationName());
		}

		if (master.getCountryID() == null && dupe.getCountryID() != null) {
			master.setCountryID(dupe.getCountryID());
		}

		if (master.getState() == null && dupe.getState() != null) {
			master.setState(dupe.getState());
		}

		if ((master.getLatitude() == null || master.getLongitude() == null)
				&& dupe.getLatitude() != null && dupe.getLongitude() != null) {
			master.setLatitude(dupe.getLatitude());
			master.setLongitude(dupe.getLongitude());
		}

		if (master.getElevation() == null && dupe.getElevation() != null) {
			master.setElevation(dupe.getElevation());
		}
	}

	public List<Station> getStations() {
		return stations;
	}
}
