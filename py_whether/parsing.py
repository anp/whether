__author__ = 'adam'

import logging

log = logging.getLogger('whether.parsing')
# defining some constants for tuple indices
# stations
STATION_ID = 0  # 'station_id'
WBAN_ID = 1  # 'wban_id'
STATION_NAME = 2  # 'station_name'
COUNTRY = 3  # 'country'
STATE = 4  # 'state'
LATITUDE = 5  # 'latitude'
LONGITUDE = 6  # 'longitude'
ELEVATION = 7  # 'elevation'


def parse_stations(filename):
    stations = {}
    found_data_start = False

    stations_file = open(filename)

    line = stations_file.readline()
    while line != '':
        line = stations_file.readline()
        if not found_data_start and line[:4] == 'USAF':
            found_data_start = True
            line = stations_file.readline()
            continue
        elif not found_data_start:
            continue

        # break it apart
        station_id_str = line[:7]
        wban_id_str = line[7:13]
        station_name = line[13:43].strip()
        country = line[43:48].strip()
        state = line[48:51].strip()
        lat_str = line[51:59].strip()
        lon_str = line[59:68].strip()
        elev_str = line[68:76].strip()

        station_name = None if station_name == '' else station_name
        country = None if country == '' else country
        state = None if state == '' else state

        # parse the relevant fields into integers, etc
        try:
            station_id = int(station_id_str)
            wban_id = int(wban_id_str)
        except ValueError:
            continue

        latitude = float(lat_str) if (lat_str != '+00.000' and lat_str != '') else None
        longitude = float(lon_str) if (lon_str != '+000.000' and lon_str != '') else None
        elevation = float(elev_str) if (elev_str != '-0999.0' and elev_str != '') else None

        key = (station_id, wban_id)
        this_station = (station_id, wban_id, station_name, country, state, latitude, longitude, elevation)

        if key not in stations:
            stations[key] = this_station
        else:
            old = stations[key]
            stations[key] = merge_stations(old, this_station)

    # close out reader
    stations_file.close()

    # find duplicate stations by lat/lon
    stations = filter_and_merge_stations_by_location(stations)
    return stations


def merge_stations(first, second):
    station_id = first[STATION_ID]
    wban_id = first[WBAN_ID]
    station_name = first[STATION_NAME] if first[STATION_NAME] != '' else second[STATION_NAME]
    country = first[COUNTRY] if first[COUNTRY] != '' else second[STATION_NAME]
    state = first[STATE] if first[STATE] != '' else second[STATE]
    latitude = first[LATITUDE] if first[LATITUDE] is not None else second[LATITUDE]
    longitude = first[LONGITUDE] if first[LONGITUDE] is not None else second[LONGITUDE]
    elevation = first[ELEVATION] if first[ELEVATION] is not None else second[ELEVATION]

    new_station = (station_id, wban_id, station_name, country, state, latitude, longitude, elevation)
    return new_station


def filter_and_merge_stations_by_location(stations):
    log.info("Number of stations initially: %d", len(stations))
    stations = sorted(list(stations.values()),
                      key=lambda station: station[LONGITUDE] if station[LONGITUDE] is not None else 200)
    deduped_stations = {}
    removed_stations = 0

    while len(stations) > 0:
        base_station = stations[0]
        del stations[0]
        key = (base_station[0], base_station[1])

        other_found = []
        for i in range(0, len(stations)):
            other_station = stations[i]
            if equal_latitude_longitude(base_station[LATITUDE], base_station[LONGITUDE],
                                        other_station[LATITUDE], other_station[LONGITUDE]):
                other_found.append(i)
                base_station = merge_stations(base_station, other_station)
            else:
                if None in (base_station[LATITUDE], other_station[LATITUDE],
                            base_station[LONGITUDE], other_station[LONGITUDE]):
                    continue

                elif abs(base_station[LATITUDE] - other_station[LATITUDE]) > 0.021 and abs(
                                base_station[LONGITUDE] - other_station[LONGITUDE]) > 0.021:
                    break

        # for each duplicate, merge it into the first one found and remove from station list
        for i in sorted(other_found, reverse=True):
            alt_key = (stations[i][0], stations[i][1])

            # maintain list of duplicate id replacements
            deduped_stations[alt_key] = base_station
            del stations[i]
            removed_stations += 1

        deduped_stations[key] = base_station

    log.info("Done removing stations! %d removed.", removed_stations)
    return deduped_stations


def equal_latitude_longitude(first_lat, first_lon, second_lat, second_lon):
    if isinstance(first_lat, float) and isinstance(first_lon, float) and isinstance(second_lat, float) and isinstance(
            second_lon, float):
        lat_error = abs(first_lat - second_lat)
        lon_error = abs(first_lon - second_lon)
        return lat_error <= 0.02 and lon_error <= 0.02
    else:
        return False


def parse_summary(file_path, stations):
    with open(file_path, 'r') as f:
        summaries = []

        # skip the first line
        for line in f.readlines()[1:]:
            # split into relevant fields
            station_id = int(line[0:6].strip())
            wban_id = int(line[7:12].strip())

            # now we need to check the map of duplicate stations to get the id that goes to the db
            this_station = stations.get((station_id, wban_id))
            if this_station is None:
                continue

            station_id, wban_id = this_station[0:2]
            date = line[14:18] + '-' + line[18:20] + '-' + line[20:22]

            mean_temp_str = line[24:30]
            mean_temp = float(mean_temp_str) if mean_temp_str != '9999.9' else None
            mean_temp_obs = int(line[31:33])

            dew_point_str = line[35:41]
            dew_point = float(dew_point_str) if dew_point_str != '9999.9' else None
            dew_point_obs = int(line[42:44])

            sea_pressure_str = line[46:52]
            sea_pressure = float(sea_pressure_str) if sea_pressure_str != '9999.9' else None
            sea_pressure_obs = int(line[53:55])

            station_pressure_str = line[57:63]
            station_pressure = float(station_pressure_str) if station_pressure_str != '9999.9' else None
            station_pressure_obs = int(line[64:66])

            visibility_str = line[68:73]
            visibility = float(visibility_str) if visibility_str != '999.9' else None
            visibility_obs = int(line[74:76])

            mean_wind_spd_str = line[78:83]
            mean_wind_spd = float(mean_wind_spd_str) if mean_wind_spd_str != '999.9' else None
            mean_wind_spd_obs = int(line[84:86])

            max_wind_spd_str = line[88:93]
            max_wind_spd = float(max_wind_spd_str) if max_wind_spd_str != '999.9' else None

            max_wind_gust_str = line[95:100]
            max_wind_gust = float(max_wind_gust_str) if max_wind_gust_str != '999.9' else None

            max_temp_str = line[102:108]
            max_temp = float(max_temp_str) if max_temp_str != '9999.9' else None
            max_temp_hourly = line[108] == '*'

            min_temp_str = line[110:116]
            min_temp = float(min_temp_str) if min_temp_str != '9999.9' else None
            min_temp_hourly = line[116] == '*'

            precip_str = line[118:123]
            precip_flag = line[123]
            precip = float(precip_str) if (
                precip_str != '99.99' and precip_flag != 'H' and precip_flag != 'I') else None

            snow_depth_str = line[125:130]
            snow_depth = float(snow_depth_str) if snow_depth_str != '999.9' else None

            fog = line[132] == '1'
            rain = line[133] == '1'
            snow = line[134] == '1'
            hail = line[135] == '1'
            thunder = line[136] == '1'
            tornado = line[137] == '1'

            summary = '\t'.join([
                str(station_id),
                str(wban_id),
                date,
                str(mean_temp),
                str(mean_temp_obs),
                str(max_temp),
                str(max_temp_hourly),
                str(min_temp),
                str(min_temp_hourly),
                str(dew_point),
                str(dew_point_obs),
                str(sea_pressure),
                str(sea_pressure_obs),
                str(station_pressure),
                str(station_pressure_obs),
                str(visibility),
                str(visibility_obs),
                str(mean_wind_spd),
                str(mean_wind_spd_obs),
                str(max_wind_spd),
                str(max_wind_gust),
                str(precip),
                precip_flag,
                str(snow_depth),
                str(fog),
                str(rain),
                str(snow),
                str(hail),
                str(thunder),
                str(tornado)
            ]) + '\n'
            summary = summary.encode('utf-8')

            """
            summary = (station_id, wban_id, date, mean_temp, mean_temp_obs, dew_point, dew_point_obs, sea_pressure,
                       sea_pressure_obs, station_pressure, station_pressure_obs, visibility, visibility_obs,
                       mean_wind_spd, mean_wind_spd_obs, max_wind_spd, max_wind_gust, max_temp, max_temp_hourly,
                       min_temp, min_temp_hourly, precip, precip_flag, snow_depth, fog, rain, snow, hail, thunder,
                       tornado)
            """
            summaries.append(summary)

        return summaries
