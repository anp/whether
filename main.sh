#!/bin/bash
docker build -t whether .

VOLUME_PATH=`pwd`/volume:/whether
mkdir -p ./volume
docker run -v $VOLUME_PATH whether
echo "All done! See $VOLUME_PATH/plots for the output."
