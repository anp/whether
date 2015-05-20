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

rm -dr ~/gsod/untarred

