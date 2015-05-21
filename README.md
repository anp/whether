# whether
Data pipeline and visualization tool for public weather data.

To download and unpack all of the NCDC GSOD data files in preparation for processing:
```
./main.sh
```
To run the processor manually:
```
gradle clean runProcessor
```
In the near future, the Dockerfile will be functional and the entire process will run inside a purpose-built docker image.
