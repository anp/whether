#!/bin/bash

#This script downloads all NCDC GSOD data to ./gsod/

#fail on error and report error of failing command
set -e

#make an output DIR
mkdir -p ~/gsod

echo
echo "******************************************************************************"
echo "******************        Downloading NCDC GSOD TARs       *******************"
echo "******************************************************************************"
echo

echo "Downloading list of weather stations & their metadata..."
wget -nv -O ~/gsod/isd-history.txt ftp://ftp.ncdc.noaa.gov/pub/data/gsod/isd-history.txt

echo "Downloading GSOD annual tarballs from NCDC..."

for YEAR in `seq 1973 2014`
do
	wget -nv -O ~/gsod/gsod_${YEAR}.tar ftp://ftp.ncdc.noaa.gov/pub/data/gsod/${YEAR}/gsod_${YEAR}.tar
done
