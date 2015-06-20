res <- dbSendQuery(conn = con, statement = "
				   select
  s.latitude,
  s.longitude,
  stddev_pop(ds.mean_temp_fahr)
from stations s, daily_summaries ds
WHERE ds.station_id = s.station_id and ds.wban_id = s.wban_id
GROUP BY s.latitude, s.longitude")

print("Fetching temperature std. dev. query results...")
stdDevTempLocationFrame <- dbFetch(res)
dbClearResult(res)

baseMap <- ggplot() + borders("world", colour="gray15", fill="gray90")
stdDevMap <- baseMap +
geom_point(aes(x=stdDevTempLocationFrame$longitude, y=stdDevTempLocationFrame$latitude, color= stdDevTempLocationFrame$stddev_pop), alpha=0.95, size=3) +
	scale_color_gradient(low="black", high="yellow", na.value = "red4", name="Std. Dev.") +
	ggtitle("Temperature Standard Deviation 1973-2014") +
	theme(plot.title = element_text(size=18, face="bold", vjust=2), axis.ticks.y = element_blank(), axis.text.y = element_blank(), axis.ticks.x = element_blank(), axis.text.x = element_blank()) +
	labs(x="", y="")

plot(stdDevMap)
ggsave(filename = 'temp_std_dev_map.png', path=plotOutputPath, width=17.2, height=10.7)

plot(ggplot(stdDevTempLocationFrame, aes(x=stdDevTempLocationFrame$latitude, y=stdDevTempLocationFrame$stddev_pop)) + geom_point(alpha=0.5) + geom_smooth())
ggsave(filename = 'temp_std_dev_vs_latitude.png', path=plotOutputPath, width=17.2, height=10.7)
