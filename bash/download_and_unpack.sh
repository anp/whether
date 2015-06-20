#!/bin/sh
mkdir -p /whether

echo "Downloading list of weather stations & their metadata..."
wget -nv -O /whether/isd-history.txt ftp://ftp.ncdc.noaa.gov/pub/data/gsod/isd-history.txt

echo "Downloading GSOD annual tarballs from NCDC..."

mkdir /whether/tars
for YEAR in `seq 1973 2014`
do
	if [ ! -f /whether/tars/gsod_${YEAR}.tar ]; then
		wget -nv -O /whether/tars/gsod_${YEAR}.tar ftp://ftp.ncdc.noaa.gov/pub/data/gsod/${YEAR}/gsod_${YEAR}.tar
	fi
done

mkdir -p /whether/untarred
mkdir -p /whether/unzipped

echo "Unpacking each year's data..."
cat /whether/tars/*.tar | tar --skip-old-files -xf - -i -C /whether/untarred

echo "Unzipping each station's data for each year..."
cd /whether/untarred

for GZIP_FILE in `ls`
do

	if [ ! -f /whether/unzipped/$(basename "${GZIP_FILE}" .gz) ]; then
		zcat $GZIP_FILE > /whether/unzipped/$(basename "${GZIP_FILE}" .gz)
	fi
done
