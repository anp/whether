# whether

Data pipeline and visualization tool for global weather data. Built on Python, R, PostgreSQL, and a little shell scripting. Uses Docker to automate the process and smoothly handle dependencies.

`whether` (as of right now) is an automated end-to-end process:

1. Download weather station daily summaries for the globe from 1973-2014 from [NOAA GSOD](https://data.noaa.gov/dataset/global-surface-summary-of-the-day-gsod).
2. Unpack all of the compressed tarballs into their text format.
3. Parse all files into a Postgres database with some Python for further querying (dataset is ~23GB uncompressed in text, won't fit in RAM on most consumer hardware).
4. Query database to create several visualizations using R.

It's very easy to add R scripts to create extra visualizations. See `r_whether/plotMeanTemp.R` for an example. Once the script is written (and in the `r_whether` directory), just add it to `r_whether/main.R`:

```
source('newRVisualizationScript.R')
```

Of course it's also entirely feasible to configure dependencies manually, use the bash and Python scripts to create the database, and run the R scripts (and other arbitrary queries) against it without running `whether` completely end-to-end. Avoiding the end-to-end process would leave you with a permanent datastore of the GSOD data. Currently since everything is processed in containers, and I don't anticipate having many users aside from basic testing, all database and text files (not saved visualizations) are deleted as part of the automatic process.

This tool would be easily run in the cloud (for example on [AWS ECS](http://aws.amazon.com/ecs/)), although in that case I would need to disable the container-local postgres instance and point it to an [AWS RDS](http://aws.amazon.com/rds/) instance. 

## Try it out

To run `whether`, first make sure [Docker](https://docs.docker.com/installation/) is installed and working for your operating system. Then:

```
$ git clone https://github.com/dikaiosune/whether.git
$ cd whether
$ ./main.sh
```

This takes about ~4 hours on my machine (i5-4570K, Samsung T1 256GB USB3 SSD). This time is primarily bounded by the NOAA FTP server (download takes about 1-1.5 hours, even on a very fast fiber connection), and local IO once that's downloaded. About 40-50GB of free space is recommended, preferably on a fast SSD. The scripts remove both the database files and intermediate text files from your drive during processing, but that space is needed to parse everything out. I considered attempting to do everything in memory, but I wanted to keep the memory footprint low (less than 2GB in my tests so far). If you need the database to be persistent, you could map the container's `/var/lib/postgresql/data` to a local directory (add a `-v LOCAL_PATH:/var/lib/postgresql` flag to `main.sh:6`) and comment out `bash/whether.sh:27`. If you want to preserve the unpacked text files, comment out `bash/whether.sh:19`, and they'll be saved under `volume/unzipped`.

Output is saved to `whether/volume/plots`. Examples are in that directory of this repo which were obtained by the above process on my machine.

## Misc.

I believe that the NOAA data is not currently legal for export, so beware when running this outside of the US. No export-restricted data is included in this distribution, but it contains automated processes for downloading it.

Pull requests are certainly welcome, but I don't intend to do much expansion on this project. I'll be adding more visualizations/regressions/etc. as I think of interesting ones, but I don't have any current plans to turn this into a big enterprise-y, configurable beast.

Questions, comments, hate-mail? adam dot n dot perry at gmail dot com.
