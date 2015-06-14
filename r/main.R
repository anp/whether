library(DBI)
library("ggmap")
source("./config.R")

print("Connecting to PG database and preparing query...")



res <- dbSendQuery(conn = con, statement = "select
  s.latitude,
  s.longitude,
  avg(ds.mean_temp_fahr)
from stations s, daily_summaries ds
WHERE ds.station_id = s.station_id and ds.wban_id = s.wban_id
GROUP BY s.latitude, s.longitude")

print("Fetching query results...")
avgTempLocationFrame <- dbFetch(res)
dbClearResult(res)

source(plotMeanTemp.R)

res <- dbSendQuery(conn = con, statement = "
				   select
  s.latitude,
  s.longitude,
  stddev_pop(ds.mean_temp_fahr)
from stations s, daily_summaries ds
WHERE ds.station_id = s.station_id and ds.wban_id = s.wban_id
GROUP BY s.latitude, s.longitude")

stdDevTempLocationFrame <- dbFetch(res)

res <- dbSendQuery(conn = con, statement = "
				   select
  s.latitude,
  s.longitude,
  count(ds.*)
from stations s, daily_summaries ds
WHERE ds.station_id = s.station_id and ds.wban_id = s.wban_id and ds.tornado_reported = TRUE
GROUP BY s.latitude, s.longitude
")

tornadoCountLocationFrame <- dbFetch(res)

dbDisconnect(con)
