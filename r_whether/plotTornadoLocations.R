res <- dbSendQuery(conn = con, statement = "
				   select
  s.latitude,
  s.longitude,
  count(ds.*)
from stations s, daily_summaries ds
WHERE ds.station_id = s.station_id and ds.wban_id = s.wban_id and ds.tornado_reported = TRUE
GROUP BY s.latitude, s.longitude
")

print("Fetching tornado frequency query results...")
tornadoCountLocationFrame <- dbFetch(res)

tornadoMap <- baseMap +
	geom_point(aes(x=tornadoCountLocationFrame$longitude, y=tornadoCountLocationFrame$latitude), alpha=0.3, color="blue", size= (tornadoCountLocationFrame$count/10)) +
	ggtitle("Tornados Reported 1973-2014") +
	theme(plot.title = element_text(size=18, face="bold", vjust=2), axis.ticks.y = element_blank(), axis.text.y = element_blank(), axis.ticks.x = element_blank(), axis.text.x = element_blank()) +
	labs(x="", y="")

plot(tornadoMap)
ggsave(filename='tornado_frequency_map.png', path=plotOutputPath, width=17.2, height=10.7)
