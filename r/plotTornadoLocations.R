baseMap <- ggplot() + borders("world", colour="gray15", fill="gray90")
map <- baseMap +
	geom_point(aes(x=tornadoCountLocationFrame$longitude, y=tornadoCountLocationFrame$latitude), alpha=0.3, color="blue", size= (tornadoCountLocationFrame$count/10))
plot(map)
