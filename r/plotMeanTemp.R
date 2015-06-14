baseMap <- ggplot() + borders("world", colour="gray15", fill="gray90")
map <- baseMap +
	geom_point(aes(x=avgTempLocationFrame$longitude, y=avgTempLocationFrame$latitude, color= avgTempLocationFrame$avg), alpha=0.95, size=3) +
	scale_color_gradient(low="red4", high="yellow", na.value = "red4", limits=c(25,100))
plot(map)
