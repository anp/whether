#file::config.R
dbName <- 'whether'
dbHost <- 'localhost'
dbPort <- 5432
dbUser <- 'postgres'
dbPass <- 'whether_password'

baseMap <- ggplot() + borders("world", colour="gray15", fill="gray90")

plotOutputPath <- '/whether/plots'
