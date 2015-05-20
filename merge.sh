#!/bin/bash

echo "Ignore any errors from rm here, cleaning up any old working directories..."
rm -dr ~/gsod/untarred ~/gsod/unzipped ~/gsod/merged

set -e

echo "Creating working directories..."
mkdir -p ~/gsod/untarred
mkdir -p ~/gsod/unzipped
mkdir -p ~/gsod/merged

echo "Unpacking each year's data..."
cat ~/gsod/*.tar | tar -xf - -i -C ~/gsod/untarred

echo "Unzipping each station's data for each year..."
cd ~/gsod/untarred

for GZIP_FILE in `ls`
do
	zcat $GZIP_FILE > ~/gsod/unzipped/$(basename "${GZIP_FILE}" .gz)
done

cd ~/gsod/
rm -dr ~/gsod/untarred

echo "Seeding merge process..."
for OP_FILE_INIT in `ls -1 ~/gsod/unzipped/*-1973.op`
do
	mv $OP_FILE_INIT ~/gsod/merged/${$OP_FILE_INIT#'-1973'}
done

echo "Merging files..."

echo "Nothing happening here yet..."

echo "Done merging!"
