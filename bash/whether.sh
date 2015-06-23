#!/bin/sh
mkdir -p $PGDATA

chown -R postgres "$PGDATA"
gosu postgres initdb
gosu postgres pg_ctl -o "-p 5432" start

echo "Downloading and processing data files..."
./download_and_unpack.sh

echo "Files processed. Initializing database and parsing files..."

gosu postgres psql postgres -c "alter user postgres with password '$PGPASS';"
gosu postgres createdb whether

echo "Parsing files into database..."
python3 py_whether/processor.py whether.ini
echo "Files parsed to database."
rm -drf /whether/unzipped

echo "Running some basic analysis..."
mkdir /whether/plots
Rscript main.R
echo "Maps and plots saved."

gosu postgres pg_ctl stop
rm -drf $PGDATA
