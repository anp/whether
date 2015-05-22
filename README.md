# whether
Data pipeline and visualization tool for public weather data.


To download, unpack and parse all of the NCDC GSOD data files in preparation for processing:
```
./main.sh
```

This will (in future repo updates) also insert database records and create visualizations of the data.

Here's the current output of the process running on a MacBook Pro with storage on a Samsung T1 USB SSD:

```
******************************************************************************
******************        Downloading NCDC GSOD TARs       *******************
******************************************************************************

Downloading list of weather stations & their metadata...
2015-05-22 12:55:35 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/isd-history.txt [7755224] -> "~/gsod/isd-history.txt" [1]
Downloading GSOD annual tarballs from NCDC...
2015-05-22 12:56:00 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1973/gsod_1973.tar [56750080] -> "~/gsod/gsod_1973.tar" [1]
2015-05-22 12:56:25 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1974/gsod_1974.tar [57600000] -> "~/gsod/gsod_1974.tar" [1]
2015-05-22 12:56:56 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1975/gsod_1975.tar [59985920] -> "~/gsod/gsod_1975.tar" [1]
2015-05-22 12:57:07 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1976/gsod_1976.tar [61480960] -> "~/gsod/gsod_1976.tar" [1]
2015-05-22 12:57:30 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1977/gsod_1977.tar [61788160] -> "~/gsod/gsod_1977.tar" [1]
2015-05-22 12:57:48 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1978/gsod_1978.tar [61726720] -> "~/gsod/gsod_1978.tar" [1]
2015-05-22 12:58:42 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1979/gsod_1979.tar [62361600] -> "~/gsod/gsod_1979.tar" [1]
2015-05-22 12:59:34 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1980/gsod_1980.tar [61562880] -> "~/gsod/gsod_1980.tar" [1]
2015-05-22 13:00:01 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1981/gsod_1981.tar [62351360] -> "~/gsod/gsod_1981.tar" [1]
2015-05-22 13:00:48 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1982/gsod_1982.tar [62064640] -> "~/gsod/gsod_1982.tar" [1]
2015-05-22 13:01:36 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1983/gsod_1983.tar [64122880] -> "~/gsod/gsod_1983.tar" [1]
2015-05-22 13:01:58 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1984/gsod_1984.tar [66375680] -> "~/gsod/gsod_1984.tar" [1]
2015-05-22 13:02:41 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1985/gsod_1985.tar [67993600] -> "~/gsod/gsod_1985.tar" [1]
2015-05-22 13:03:23 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1986/gsod_1986.tar [68884480] -> "~/gsod/gsod_1986.tar" [1]
2015-05-22 13:03:46 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1987/gsod_1987.tar [71075840] -> "~/gsod/gsod_1987.tar" [1]
2015-05-22 13:05:25 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1988/gsod_1988.tar [72570880] -> "~/gsod/gsod_1988.tar" [1]
2015-05-22 13:05:36 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1989/gsod_1989.tar [72837120] -> "~/gsod/gsod_1989.tar" [1]
2015-05-22 13:05:48 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1990/gsod_1990.tar [75479040] -> "~/gsod/gsod_1990.tar" [1]
2015-05-22 13:07:12 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1991/gsod_1991.tar [73963520] -> "~/gsod/gsod_1991.tar" [1]
2015-05-22 13:07:43 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1992/gsod_1992.tar [72294400] -> "~/gsod/gsod_1992.tar" [1]
2015-05-22 13:08:08 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1993/gsod_1993.tar [72949760] -> "~/gsod/gsod_1993.tar" [1]
2015-05-22 13:09:24 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1994/gsod_1994.tar [73574400] -> "~/gsod/gsod_1994.tar" [1]
2015-05-22 13:10:22 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1995/gsod_1995.tar [71782400] -> "~/gsod/gsod_1995.tar" [1]
2015-05-22 13:10:33 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1996/gsod_1996.tar [70901760] -> "~/gsod/gsod_1996.tar" [1]
2015-05-22 13:11:09 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1997/gsod_1997.tar [71229440] -> "~/gsod/gsod_1997.tar" [1]
2015-05-22 13:11:55 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1998/gsod_1998.tar [71127040] -> "~/gsod/gsod_1998.tar" [1]
2015-05-22 13:13:25 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/1999/gsod_1999.tar [71383040] -> "~/gsod/gsod_1999.tar" [1]
2015-05-22 13:14:18 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2000/gsod_2000.tar [69826560] -> "~/gsod/gsod_2000.tar" [1]
2015-05-22 13:14:36 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2001/gsod_2001.tar [73625600] -> "~/gsod/gsod_2001.tar" [1]
2015-05-22 13:16:20 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2002/gsod_2002.tar [76840960] -> "~/gsod/gsod_2002.tar" [1]
2015-05-22 13:17:50 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2003/gsod_2003.tar [77752320] -> "~/gsod/gsod_2003.tar" [1]
2015-05-22 13:18:08 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2004/gsod_2004.tar [80793600] -> "~/gsod/gsod_2004.tar" [1]
2015-05-22 13:20:03 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2005/gsod_2005.tar [93030400] -> "~/gsod/gsod_2005.tar" [1]
2015-05-22 13:20:41 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2006/gsod_2006.tar [87418880] -> "~/gsod/gsod_2006.tar" [1]
2015-05-22 13:21:42 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2007/gsod_2007.tar [88842240] -> "~/gsod/gsod_2007.tar" [1]
2015-05-22 13:22:57 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2008/gsod_2008.tar [92426240] -> "~/gsod/gsod_2008.tar" [1]
2015-05-22 13:25:48 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2009/gsod_2009.tar [95385600] -> "~/gsod/gsod_2009.tar" [1]
2015-05-22 13:28:41 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2010/gsod_2010.tar [98201600] -> "~/gsod/gsod_2010.tar" [1]
2015-05-22 13:29:17 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2011/gsod_2011.tar [99461120] -> "~/gsod/gsod_2011.tar" [1]
2015-05-22 13:30:55 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2012/gsod_2012.tar [104826880] -> "~/gsod/gsod_2012.tar" [1]
2015-05-22 13:32:03 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2013/gsod_2013.tar [105748480] -> "~/gsod/gsod_2013.tar" [1]
2015-05-22 13:33:40 URL: ftp://ftp.ncdc.noaa.gov/pub/data/gsod/2014/gsod_2014.tar [108861440] -> "~/gsod/gsod_2014.tar" [1]
Ignore any errors from rm here, cleaning up any old working directories...
Creating working directories...
rm: cannot remove ‘~/gsod/untarred’: No such file or directory
rm: cannot remove ‘~/gsod/unzipped’: No such file or directory
rm: cannot remove ‘~/gsod/merged’: No such file or directory
Unpacking each year's data...
Unzipping each station's data for each year...
Starting a new Gradle Daemon for this build (subsequent builds will be faster).
:clean
:compileJava
:processResources
:classes
:jar
:capsule
:runProcessor
14:26:54.400 [main] INFO  io.whether.Whether - 395374 GSOD files found.
14:27:01.093 [main] DEBUG io.whether.Whether - 1% of GSOD files parsed, 5000/395374 files completed.
14:27:06.792 [main] DEBUG io.whether.Whether - 2% of GSOD files parsed, 10000/395374 files completed.
14:27:12.612 [main] DEBUG io.whether.Whether - 3% of GSOD files parsed, 15000/395374 files completed.
14:27:18.496 [main] DEBUG io.whether.Whether - 5% of GSOD files parsed, 20000/395374 files completed.
14:27:24.255 [main] DEBUG io.whether.Whether - 6% of GSOD files parsed, 25000/395374 files completed.
14:27:29.840 [main] DEBUG io.whether.Whether - 7% of GSOD files parsed, 30000/395374 files completed.
14:27:36.655 [main] DEBUG io.whether.Whether - 8% of GSOD files parsed, 35000/395374 files completed.
14:27:42.919 [main] DEBUG io.whether.Whether - 10% of GSOD files parsed, 40000/395374 files completed.
14:27:48.523 [main] DEBUG io.whether.Whether - 11% of GSOD files parsed, 45000/395374 files completed.
14:27:54.410 [main] DEBUG io.whether.Whether - 12% of GSOD files parsed, 50000/395374 files completed.
14:28:00.549 [main] DEBUG io.whether.Whether - 13% of GSOD files parsed, 55000/395374 files completed.
14:28:07.136 [main] DEBUG io.whether.Whether - 15% of GSOD files parsed, 60000/395374 files completed.
14:28:14.461 [main] DEBUG io.whether.Whether - 16% of GSOD files parsed, 65000/395374 files completed.
14:28:21.060 [main] DEBUG io.whether.Whether - 17% of GSOD files parsed, 70000/395374 files completed.
14:28:27.844 [main] DEBUG io.whether.Whether - 18% of GSOD files parsed, 75000/395374 files completed.
14:28:34.497 [main] DEBUG io.whether.Whether - 20% of GSOD files parsed, 80000/395374 files completed.
14:28:40.940 [main] DEBUG io.whether.Whether - 21% of GSOD files parsed, 85000/395374 files completed.
14:28:47.828 [main] DEBUG io.whether.Whether - 22% of GSOD files parsed, 90000/395374 files completed.
14:28:54.426 [main] DEBUG io.whether.Whether - 24% of GSOD files parsed, 95000/395374 files completed.
14:29:01.126 [main] DEBUG io.whether.Whether - 25% of GSOD files parsed, 100000/395374 files completed.
14:29:07.556 [main] DEBUG io.whether.Whether - 26% of GSOD files parsed, 105000/395374 files completed.
14:29:14.268 [main] DEBUG io.whether.Whether - 27% of GSOD files parsed, 110000/395374 files completed.
14:29:21.014 [main] DEBUG io.whether.Whether - 29% of GSOD files parsed, 115000/395374 files completed.
14:29:28.378 [main] DEBUG io.whether.Whether - 30% of GSOD files parsed, 120000/395374 files completed.
14:29:34.936 [main] DEBUG io.whether.Whether - 31% of GSOD files parsed, 125000/395374 files completed.
14:29:41.859 [main] DEBUG io.whether.Whether - 32% of GSOD files parsed, 130000/395374 files completed.
14:29:48.508 [main] DEBUG io.whether.Whether - 34% of GSOD files parsed, 135000/395374 files completed.
14:29:54.988 [main] DEBUG io.whether.Whether - 35% of GSOD files parsed, 140000/395374 files completed.
14:30:02.125 [main] DEBUG io.whether.Whether - 36% of GSOD files parsed, 145000/395374 files completed.
14:30:08.763 [main] DEBUG io.whether.Whether - 37% of GSOD files parsed, 150000/395374 files completed.
14:30:15.555 [main] DEBUG io.whether.Whether - 39% of GSOD files parsed, 155000/395374 files completed.
14:30:22.303 [main] DEBUG io.whether.Whether - 40% of GSOD files parsed, 160000/395374 files completed.
14:30:28.969 [main] DEBUG io.whether.Whether - 41% of GSOD files parsed, 165000/395374 files completed.
14:30:36.112 [main] DEBUG io.whether.Whether - 42% of GSOD files parsed, 170000/395374 files completed.
14:30:42.720 [main] DEBUG io.whether.Whether - 44% of GSOD files parsed, 175000/395374 files completed.
14:30:49.301 [main] DEBUG io.whether.Whether - 45% of GSOD files parsed, 180000/395374 files completed.
14:30:55.941 [main] DEBUG io.whether.Whether - 46% of GSOD files parsed, 185000/395374 files completed.
14:31:03.011 [main] DEBUG io.whether.Whether - 48% of GSOD files parsed, 190000/395374 files completed.
14:31:10.070 [main] DEBUG io.whether.Whether - 49% of GSOD files parsed, 195000/395374 files completed.
14:31:16.827 [main] DEBUG io.whether.Whether - 50% of GSOD files parsed, 200000/395374 files completed.
14:31:23.780 [main] DEBUG io.whether.Whether - 51% of GSOD files parsed, 205000/395374 files completed.
14:31:30.495 [main] DEBUG io.whether.Whether - 53% of GSOD files parsed, 210000/395374 files completed.
14:31:37.445 [main] DEBUG io.whether.Whether - 54% of GSOD files parsed, 215000/395374 files completed.
14:31:44.244 [main] DEBUG io.whether.Whether - 55% of GSOD files parsed, 220000/395374 files completed.
14:31:51.487 [main] DEBUG io.whether.Whether - 56% of GSOD files parsed, 225000/395374 files completed.
14:31:58.393 [main] DEBUG io.whether.Whether - 58% of GSOD files parsed, 230000/395374 files completed.
14:32:05.046 [main] DEBUG io.whether.Whether - 59% of GSOD files parsed, 235000/395374 files completed.
14:32:11.698 [main] DEBUG io.whether.Whether - 60% of GSOD files parsed, 240000/395374 files completed.
14:32:18.412 [main] DEBUG io.whether.Whether - 61% of GSOD files parsed, 245000/395374 files completed.
14:32:25.324 [main] DEBUG io.whether.Whether - 63% of GSOD files parsed, 250000/395374 files completed.
14:32:31.782 [main] DEBUG io.whether.Whether - 64% of GSOD files parsed, 255000/395374 files completed.
14:32:38.459 [main] DEBUG io.whether.Whether - 65% of GSOD files parsed, 260000/395374 files completed.
14:32:44.867 [main] DEBUG io.whether.Whether - 67% of GSOD files parsed, 265000/395374 files completed.
14:32:51.718 [main] DEBUG io.whether.Whether - 68% of GSOD files parsed, 270000/395374 files completed.
14:32:58.789 [main] DEBUG io.whether.Whether - 69% of GSOD files parsed, 275000/395374 files completed.
14:33:05.564 [main] DEBUG io.whether.Whether - 70% of GSOD files parsed, 280000/395374 files completed.
14:33:12.368 [main] DEBUG io.whether.Whether - 72% of GSOD files parsed, 285000/395374 files completed.
14:33:19.125 [main] DEBUG io.whether.Whether - 73% of GSOD files parsed, 290000/395374 files completed.
14:33:26.360 [main] DEBUG io.whether.Whether - 74% of GSOD files parsed, 295000/395374 files completed.
14:33:33.139 [main] DEBUG io.whether.Whether - 75% of GSOD files parsed, 300000/395374 files completed.
14:33:39.996 [main] DEBUG io.whether.Whether - 77% of GSOD files parsed, 305000/395374 files completed.
14:33:46.752 [main] DEBUG io.whether.Whether - 78% of GSOD files parsed, 310000/395374 files completed.
14:33:53.584 [main] DEBUG io.whether.Whether - 79% of GSOD files parsed, 315000/395374 files completed.
14:34:00.983 [main] DEBUG io.whether.Whether - 80% of GSOD files parsed, 320000/395374 files completed.
14:34:07.944 [main] DEBUG io.whether.Whether - 82% of GSOD files parsed, 325000/395374 files completed.
14:34:14.952 [main] DEBUG io.whether.Whether - 83% of GSOD files parsed, 330000/395374 files completed.
14:34:21.860 [main] DEBUG io.whether.Whether - 84% of GSOD files parsed, 335000/395374 files completed.
14:34:28.645 [main] DEBUG io.whether.Whether - 85% of GSOD files parsed, 340000/395374 files completed.
14:34:35.850 [main] DEBUG io.whether.Whether - 87% of GSOD files parsed, 345000/395374 files completed.
14:34:42.376 [main] DEBUG io.whether.Whether - 88% of GSOD files parsed, 350000/395374 files completed.
14:34:49.133 [main] DEBUG io.whether.Whether - 89% of GSOD files parsed, 355000/395374 files completed.
14:34:55.884 [main] DEBUG io.whether.Whether - 91% of GSOD files parsed, 360000/395374 files completed.
14:35:02.756 [main] DEBUG io.whether.Whether - 92% of GSOD files parsed, 365000/395374 files completed.
14:35:09.809 [main] DEBUG io.whether.Whether - 93% of GSOD files parsed, 370000/395374 files completed.
14:35:16.596 [main] DEBUG io.whether.Whether - 94% of GSOD files parsed, 375000/395374 files completed.
14:35:23.480 [main] DEBUG io.whether.Whether - 96% of GSOD files parsed, 380000/395374 files completed.
14:35:30.458 [main] DEBUG io.whether.Whether - 97% of GSOD files parsed, 385000/395374 files completed.
14:35:37.357 [main] DEBUG io.whether.Whether - 98% of GSOD files parsed, 390000/395374 files completed.
14:35:44.715 [main] DEBUG io.whether.Whether - 99% of GSOD files parsed, 395000/395374 files completed.

BUILD SUCCESSFUL

Total time: 8 mins 59.96 secs

real	100m13.407s
user	16m6.453s
sys	14m46.003s
```
