__author__ = 'adam'

import configparser
import os
from sys import argv

import psycopg2

import py_whether.db as db










# defining some constants for dict keys
STATION_ID = 'station_id'
WBAN_ID = 'wban_id'
STATION_NAME = 'station_name'
COUNTRY = 'country'
STATE = 'state'
LATITUDE = 'latitude'
LONGITUDE = 'longitude'
ELEVATION = 'elevation'

# defining some functions for breaking functionality out
def parse_stations(file):
    stations = {}
    found_data_start = False

    line = file.readline()
    while line != '':
        line = file.readline()
        if not found_data_start and line[:4] == 'USAF':
            found_data_start = True
            line = file.readline()
            continue
        elif not found_data_start:
            continue

        # break it apart
        station_id_str = line[:7].strip()
        wban_id_str = line[7:13].strip()
        station_name = line[13:43].strip()
        country = line[43:48].strip()
        state = line[48:51].strip()
        lat_str = line[51:59].strip()
        lon_str = line[59:68].strip()
        elev_str = line[68:76].strip()

        # parse the relevant fields into integers, etc
        try:
            station_id = int(station_id_str)
            wban_id = int(wban_id_str)
        except ValueError:
            continue

        latitude = None
        longitude = None
        elevation = None
        try:
            if lat_str != '+00.000':
                latitude = float(lat_str)
            if lon_str != '+000.000':
                longitude = float(lon_str)
        except ValueError:
            pass

        try:
            if elev_str != '-0999.0':
                elevation = float(elev_str)
        except ValueError:
            pass

        key = (station_id, wban_id)
        this_station = {STATION_ID: station_id, WBAN_ID: wban_id, STATION_NAME: station_name, COUNTRY: country,
                        STATE: state, LATITUDE: latitude, LONGITUDE: longitude, ELEVATION: elevation}

        if key not in stations:
            stations[key] = this_station
        else:
            old = stations[key]
            stations[key] = merge_stations(old, this_station)

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

    new_station = {STATION_ID: station_id,
                   WBAN_ID: wban_id,
                   STATION_NAME: station_name,
                   COUNTRY: country,
                   STATE: state,
                   LATITUDE: latitude,
                   LONGITUDE: longitude,
                   ELEVATION: elevation}
    return new_station


def filter_and_merge_stations_by_location(stations):
    print("Number of stations initially: ", len(stations))
    stations = sorted(list(stations.copy().values()),
                      key=lambda station: station[LONGITUDE] if station[LONGITUDE] is not None else 200)
    deduped_stations = {}
    removed_stations = 0

    while len(stations) > 0:
        base_station = stations[0]
        del stations[0]
        key = (base_station[STATION_ID], base_station[WBAN_ID])

        other_found = list()
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

        for i in sorted(other_found, reverse=True):
            alt_key = (stations[i][STATION_ID], stations[i][WBAN_ID])
            deduped_stations[alt_key] = base_station
            del stations[i]
            removed_stations += 1

        deduped_stations[key] = base_station

    print("Done removing stations!", removed_stations, "removed.")
    return deduped_stations


def equal_latitude_longitude(first_lat, first_lon, second_lat, second_lon):
    if isinstance(first_lat, float) and isinstance(first_lon, float) and isinstance(second_lat, float) and isinstance(
            second_lon, float):
        lat_error = abs(first_lat - second_lat)
        lon_error = abs(first_lon - second_lon)
        return lat_error <= 0.02 and lon_error <= 0.02
    else:
        return False

# read configuration
config = configparser.ConfigParser()

config.read(argv[1:])
gsod_conf = config['GSOD FILES']

pg_conf = config['POSTGRES']
gsod_root = gsod_conf['base_folder']
station_inventory = gsod_conf['station_inventory']

gsod_unzipped = gsod_conf['unpacked_folder']
pg_host = pg_conf['host']
pg_port = pg_conf['port']
pg_db = pg_conf['db']
pg_user = pg_conf['user']

pg_pass = pg_conf['pass']
# instantiate database connection
conn = psycopg2.connect(database=pg_db, user=pg_user, password=pg_pass, host=pg_host, port=pg_port)
cur = conn.cursor()

print("Connected to database!")
# init db schema
db.create_schema(cur)

conn.commit()
# find station inventory
stations_file = open(gsod_root + os.sep + station_inventory)

# parse station inventory
stations_dict = parse_stations(stations_file)
# close out reader
# find duplicate stations by lat/lon
# for each duplicate, merge it into the first one found and remove from station list
# maintain list of duplicate id replacements
# for each station, persist to database


# find the folder of unpacked GSOD files
# find all files that end in .op
# parse
# skip the first line
# for each line
# split into relevant fields
# parse into int/double/boolean as appropriate
# persist
# close out reader

db.database_cleanup_and_index(cur)
conn.commit()

# clean up, close out any unused resources
cur.close()
conn.close()
print("Database connection closed!")
