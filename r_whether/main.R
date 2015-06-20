library("devtools")
library("DBI")
library("RPostgres")
library("ggplot2")

source("./config.R")

print("Connecting to PG database and preparing query...")
con <- dbConnect(RPostgres::Postgres(),dbname = dbName, host = dbHost, port = dbPort, user = dbUser, password = dbPass)

source("./plotMeanTemp.R")
source("./plotTempStdDev.R")
source("./plotTornadoLocations.R")

dbDisconnect(con)
print("Database disconnected and all plots saved.")
