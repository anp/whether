#!/bin/sh

gosu postgres pg_ctl start
gosu postgres createdb whether

echo "Downloading and processing data files..."
./download_and_unpack.sh

echo "Files processed. Initializing database and parsing files..."

echo "Parsing files into database..."
python3 processor.py whether.ini
echo "Files parsed to database."

echo "Running some basic analysis..."
Rscript main.R
echo "Maps and plots saved."

gosu postgres pg_ctl stop
