#!/bin/bash

echo "Ignore any errors from rm here, cleaning up any old working directories..."

echo "Creating working directories..."
mkdir -p ~/gsod/untarred
mkdir -p ~/gsod/unzipped

echo "Unpacking each year's data..."
cat ~/gsod/*.tar | tar --skip-old-files -xf - -i -C ~/gsod/untarred

echo "Unzipping each station's data for each year..."
cd ~/gsod/untarred

for GZIP_FILE in `ls`
do

	if [ ! -f ~/gsod/unzipped/$(basename "${GZIP_FILE}" .gz) ]; then
		zcat $GZIP_FILE > ~/gsod/unzipped/$(basename "${GZIP_FILE}" .gz)
	fi
done

#rm -dr ~/gsod/untarred

