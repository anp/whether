#!/bin/bash

#build docker image
#run docker image

#get zip of visualizations from docker image
#docker cp <containerId>:/file/path/within/container /host/path/target

#shutdown docker image

#cleanup

echo "Downloading and processing data files..."

./download.sh
./unpack.sh

echo "Files processed. Initializing database and parsing files..."

mkdir -p ~/gsod/database
initdb -D ~/gsod/database -A trust
echo "Database initialized."

postgres -D ~/gsod/database &
PG_PID=$!
echo "Postgres instance started."

echo "Parsing files into database..."
gradle runProcessor
echo "Files parsed."


echo "Shutting database down..."
kill $PG_PID

echo "Database shut down, cleaning up database files..."
#rm -dr ~/gsod/database
