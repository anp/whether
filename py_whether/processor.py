__author__ = 'adam'

import configparser
from sys import argv

import psycopg2



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

# find station inventory
# parse station inventory
# find the first line that has real data
# for each line
# break it apart
# parse the relevant fields into integers, etc
# find duplicate stations (by ids)
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

# clean up, close out any unused resources
cur.close()
conn.close()
print("Database connection closed!")
