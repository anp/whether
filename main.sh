#!/bin/bash

#build docker image
#run docker image

#get zip of visualizations from docker image
#docker cp <containerId>:/file/path/within/container /host/path/target

#shutdown docker image

#cleanup

time sh -c './download.sh && ./unpack.sh && gradle clean runProcessor'
