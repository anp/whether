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

wget -O ~/gsod/isd-history.txt ftp://ftp.ncdc.noaa.gov/pub/data/noaa/isd-history.txt

for YEAR in `seq 1973 2014`
do
	wget -O ~/gsod/gsod_${YEAR}.tar ftp://ftp.ncdc.noaa.gov/pub/data/gsod/${YEAR}/gsod_${YEAR}.tar
done
