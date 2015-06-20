res <- dbSendQuery(conn = con, statement = "select
  s.latitude,
  s.longitude,
  avg(ds.mean_temp_fahr)
from stations s, daily_summaries ds
WHERE ds.station_id = s.station_id and ds.wban_id = s.wban_id
GROUP BY s.latitude, s.longitude")

print("Fetching average temperature query results...")
avgTempLocationFrame <- dbFetch(res)
dbClearResult(res)

baseMap <- ggplot() + borders("world", colour="gray15", fill="gray90")
meanTempMap <- baseMap +
	geom_point(aes(x=avgTempLocationFrame$longitude, y=avgTempLocationFrame$latitude, color= avgTempLocationFrame$avg), alpha=0.95, size=3) +
	scale_color_gradient(low="navy", high="red", na.value = "navy", limits=c(25,100), name="Temperature") +
	ggtitle("Average Temperature 1973-2014") +
	theme(plot.title = element_text(size=18, face="bold", vjust=2), axis.ticks.y = element_blank(), axis.text.y = element_blank(), axis.ticks.x = element_blank(), axis.text.x = element_blank()) +
	labs(x="", y="")

plot(meanTempMap)
ggsave(filename='mean_temperature_map.png', path=plotOutputPath, width=17.2, height=10.7)
