__author__ = 'adam'

import configparser
import os
import os.path
from sys import argv
import logging

import postgresql

import db
import parsing



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

log_file = config['LOG']['log_file']

# logger
log = logging.getLogger('whether')
log.setLevel(logging.DEBUG)

fh = logging.FileHandler(log_file)
fh.setLevel(logging.DEBUG)

ch = logging.StreamHandler()
ch.setLevel(logging.INFO)

formatter = logging.Formatter('%(asctime)s %(name)s^%(levelname)s: %(message)s')
fh.setFormatter(formatter)
ch.setFormatter(formatter)

log.addHandler(fh)
log.addHandler(ch)

# start of main execution
log.info("Starting whether parser...")

# instantiate database connection
connection = postgresql.open(user=pg_user, password=pg_pass, host=pg_host, port=pg_port, database=pg_db)
log.info("Connected to database!")

# init db schema
connection.execute(db.station_create_table_statement)
connection.execute(db.summary_create_table_statement)
log.info("Database schema initialized.")

# find station inventory
# parse station inventory
stations_dict = parsing.parse_stations(gsod_root + os.sep + station_inventory)

log.info("Preparing and persisting stations to database.")
# for each station, persist to database
unique_stations = {s for s in stations_dict.values()}

station_statement = connection.prepare(db.station_insert_statement)
station_statement.load_rows(unique_stations)

# find the folder of unpacked GSOD files
gsod_folder = gsod_root + os.sep + gsod_unzipped

# find all files that end in .op
op_files = [gsod_folder + os.sep + f for f in os.listdir(gsod_folder) if f.endswith('.op')]
log.info('Found %d summary files.', len(op_files))

chunk_size = 40000
op_chunks = [op_files[i:i + chunk_size] for i in range(0, len(op_files), chunk_size)]

number_files_persisted = 0
total_files = len(op_files)

summary_statement = connection.prepare(db.summary_copy_statement)
for chunk in op_chunks:
    summaries = []

    for file in chunk:
        # parse and persist
        summaries.extend(parsing.parse_summary(file, stations_dict))

    summary_statement.load_rows(summaries)
    number_files_persisted += len(chunk)

    # report on progress
    log.info('%d/%d files parsed and persisted.', number_files_persisted, total_files)

# summary_cur.close()

log.info('All files parsed, cleaning up and adding indexes to database...')
connection.execute(db.index_statement)
connection.execute(db.analyze_statement)

log.info('All done!')

# clean up, close out any unused resources
connection.close()
log.info("Database connection closed!")
log.info("Exiting...\n\n")
