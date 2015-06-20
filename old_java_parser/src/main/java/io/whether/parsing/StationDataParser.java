package io.whether.parsing;

import io.whether.domain.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StationDataParser {

	private static final Logger log = LogManager.getLogger(StationDataParser.class);
	private static final Map<String, Station> duplicateReplacements = new HashMap<>();
	private static List<Station> stations;
	File stationDataFile;

	public StationDataParser(File inFile) {
		stationDataFile = inFile;
		stations = new LinkedList<>();
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
					if (stationName.equals("") || stationName.contains("BOGUS")) {
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
						if (!elevStr.equals("-0999.9")) {
							elevation = Double.parseDouble(elevStr);
						}
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
					log.trace("Unable to parse integer IDs for " + usafStr + '/' + wbanStr
							+ ". Skipping as they aren't included in GSOD data as of this writing.");
				}
			}

			rdr.close();
		} catch (IOException ioe) {
			log.error("IO error when reading station data file " + stationDataFile.getAbsolutePath(), ioe);
			return false;
		}

		log.info(stations.size() + " total stations parsed.");

		Map<Station, Set<Station>> duplicates = findDuplicateStationsByLocation();

		for (Map.Entry<Station, Set<Station>> e : duplicates.entrySet()) {
			Station master = e.getKey();
			Set<Station> currDuplicates = e.getValue();
			for (Station duplicate : currDuplicates) {
				mergeStations(master, duplicate);
			}
			stations.removeAll(currDuplicates);
		}

		log.info(stations.size() + " remaining after geographic deduplication.");

		Collections.sort(stations, (o1, o2) -> o1.getStationID() - o2.getStationID());

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

	private Map<Station, Set<Station>> findDuplicateStationsByLocation() {
		List<Station> stationsSortedByLatitude = stations.stream()
				.filter(s -> s.getLatitude() != null)
				.filter(s -> s.getLongitude() != null)
				.collect(Collectors.toList());

		Collections.sort(stationsSortedByLatitude, ((o1, o2) -> {
			Double first = (o1.getLatitude() == null ? -200.0 : o1.getLatitude());
			Double second = (o2.getLatitude() == null ? -200.0 : o2.getLatitude());
			return first.compareTo(second);
		}
		));

		Map<Station, Set<Station>> allDuplicates = new HashMap<>();

		//this is about a 1km radius around the master station
		double tolerance = 0.05;

		for (int i = 0; i < stationsSortedByLatitude.size(); i++) {
			Station currBase = stationsSortedByLatitude.get(i);
			Set<Station> currDuplicates = allDuplicates.get(currBase);
			if (currDuplicates == null) {
				currDuplicates = new HashSet<>();
			}

			for (int j = i + 1; j < stationsSortedByLatitude.size(); j++) {
				Station potentialDupe = stationsSortedByLatitude.get(j);
				boolean equalLatitude = Math.abs(currBase.getLatitude() - potentialDupe.getLatitude()) <= tolerance;
				boolean equalLongitude = Math.abs(currBase.getLongitude() - potentialDupe.getLongitude()) <= tolerance;

				if (equalLatitude && equalLongitude) {
					currDuplicates.add(potentialDupe);
					duplicateReplacements.put(potentialDupe.getStationID() + "|" + potentialDupe.getWbanID(), currBase);
				} else {
					i = j;
					break;
				}
			}

			if (currDuplicates.size() > 0) {
				log.trace("Duplicates found for \n\t" + currBase.toString() + "\n\t\t" + currDuplicates.toString());
				allDuplicates.put(currBase, currDuplicates);
			}
		}

		return allDuplicates;
	}

	public List<Station> getStations() {
		return stations;
	}

	public Map<String, Station> getDuplicateReplacements() {
		return duplicateReplacements;
	}
}
