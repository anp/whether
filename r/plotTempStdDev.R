baseMap <- ggplot() + borders("world", colour="gray15", fill="gray90")
map <- baseMap +
geom_point(aes(x=stdDevTempLocationFrame$longitude, y=stdDevTempLocationFrame$latitude, color= stdDevTempLocationFrame$stddev_pop), alpha=0.95, size=3) +
	scale_color_gradient(low="black", high="yellow", na.value = "red4")
plot(map)

plot(ggplot(stdDevTempLocationFrame, aes(x=stdDevTempLocationFrame$latitude, y=stdDevTempLocationFrame$stddev_pop)) + geom_point(alpha=0.5) + geom_smooth())
