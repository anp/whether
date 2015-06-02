package io.whether.domain;

import java.time.LocalDate;

public class DailySummary {
	Station station;
	LocalDate date;

	Double meanTemperatureFahrenheit;
	int numMeanTempObservations;

	Double dewPointFahrenheit;
	int numDewPointObservations;

	Double seaLevelPressureMillibars;
	int numSeaLevelPressureObservations;

	Double stationPressureMillibars;
	int numStationPressureObservations;

	Double visibilityMiles;
	int numVisibilityObservations;

	Double meanWindSpeedKnots;
	int numMeanWindSpeedObservations;

	Double maxWindSpeedKnots;

	Double maxWindGustKnots;

	Double maxTemperatureFahrenheit;
	boolean maxTempIsFromHourlyData;

	Double minTemperatureFahrenheit;
	boolean minTempIsFromHourlyData;

	Double precipitationInches;
	String precipReportFlag;

	Double snowDepth;

	boolean fogReported;
	boolean rainOrDrizzleReported;
	boolean snowOrIcePelletsReported;
	boolean hailReported;
	boolean thunderReported;
	boolean tornadoReported;

	public DailySummary(Station newStation, LocalDate newDate) {
		station = newStation;
		date = newDate;
	}

	public Station getStation() {
		return station;
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getMeanTemperatureFahrenheit() {
		return meanTemperatureFahrenheit;
	}

	public void setMeanTemperatureFahrenheit(final Double meanTempFahr, final int numObs) {
		meanTemperatureFahrenheit = meanTempFahr;
		numMeanTempObservations = numObs;
	}

	public int getNumMeanTempObservations() {
		return numMeanTempObservations;
	}

	public Double getDewPointFahrenheit() {
		return dewPointFahrenheit;
	}

	public void setDewPointFahrenheit(Double dewPointFahr, int numObs) {
		dewPointFahrenheit = dewPointFahr;
		numDewPointObservations = numObs;
	}

	public int getNumDewPointObservations() {
		return numDewPointObservations;
	}

	public Double getSeaLevelPressureMillibars() {
		return seaLevelPressureMillibars;
	}

	public void setSeaLevelPressureMillibars(Double seaLevelPressMillibar, int numObs) {
		seaLevelPressureMillibars = seaLevelPressMillibar;
		numSeaLevelPressureObservations = numObs;
	}

	public int getNumSeaLevelPressureObservations() {
		return numSeaLevelPressureObservations;
	}

	public Double getStationPressureMillibars() {
		return stationPressureMillibars;
	}

	public void setStationPressureMillibars(Double stationPressMillibar, int numObs) {
		stationPressureMillibars = stationPressMillibar;
		numStationPressureObservations = numObs;
	}

	public int getNumStationPressureObservations() {
		return numStationPressureObservations;
	}

	public Double getVisibilityMiles() {
		return visibilityMiles;
	}

	public void setVisibilityMiles(Double visibilityInMiles, int numObs) {
		visibilityMiles = visibilityInMiles;
		numVisibilityObservations = numObs;
	}

	public int getNumVisibilityObservations() {
		return numVisibilityObservations;
	}

	public Double getMeanWindSpeedKnots() {
		return meanWindSpeedKnots;
	}

	public void setMeanWindSpeedKnots(Double meanWindSpeed, int numObs) {
		meanWindSpeedKnots = meanWindSpeed;
		numMeanWindSpeedObservations = numObs;
	}

	public int getNumMeanWindSpeedObservations() {
		return numMeanWindSpeedObservations;
	}

	public Double getMaxWindSpeedKnots() {
		return maxWindSpeedKnots;
	}

	public void setMaxWindSpeedKnots(Double maxWindSpeed) {
		maxWindSpeedKnots = maxWindSpeed;
	}

	public Double getMaxWindGustKnots() {
		return maxWindGustKnots;
	}

	public void setMaxWindGustKnots(Double maxWindGust) {
		this.maxWindGustKnots = maxWindGust;
	}

	public Double getMaxTemperatureFahrenheit() {
		return maxTemperatureFahrenheit;
	}

	public void setMaxTemperatureFahrenheit(Double maxTempFahr, boolean isFromHourlyData) {
		this.maxTemperatureFahrenheit = maxTempFahr;
		this.maxTempIsFromHourlyData = isFromHourlyData;
	}

	public boolean isMaxTempIsFromHourlyData() {
		return maxTempIsFromHourlyData;
	}

	public Double getMinTemperatureFahrenheit() {
		return minTemperatureFahrenheit;
	}

	public void setMinTemperatureFahrenheit(Double minTempFahr, boolean isFromHourlyData) {
		this.minTemperatureFahrenheit = minTempFahr;
		this.minTempIsFromHourlyData = isFromHourlyData;
	}

	public boolean isMinTempIsFromHourlyData() {
		return minTempIsFromHourlyData;
	}

	public Double getPrecipitationInches() {
		return precipitationInches;
	}

	public void setPrecipitationInches(Double precipInches, String reportFlag) {
		precipitationInches = precipInches;
		precipReportFlag = reportFlag;
	}

	public String getPrecipReportFlag() {
		return precipReportFlag;
	}

	public Double getSnowDepth() {
		return snowDepth;
	}

	public void setSnowDepth(Double snowDepthInches) {
		snowDepth = snowDepthInches;
	}

	public boolean isFogReported() {
		return fogReported;
	}

	public void setFogReported(boolean reported) {
		this.fogReported = reported;
	}

	public boolean isRainOrDrizzleReported() {
		return rainOrDrizzleReported;
	}

	public void setRainOrDrizzleReported(boolean reported) {
		this.rainOrDrizzleReported = reported;
	}

	public boolean isSnowOrIcePelletsReported() {
		return snowOrIcePelletsReported;
	}

	public void setSnowOrIcePelletsReported(boolean reported) {
		this.snowOrIcePelletsReported = reported;
	}

	public boolean isHailReported() {
		return hailReported;
	}

	public void setHailReported(boolean reported) {
		this.hailReported = reported;
	}

	public boolean isThunderReported() {
		return thunderReported;
	}

	public void setThunderReported(boolean reported) {
		this.thunderReported = reported;
	}

	public boolean isTornadoReported() {
		return tornadoReported;
	}

	public void setTornadoReported(boolean reported) {
		this.tornadoReported = reported;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		DailySummary that = (DailySummary) o;

		return station.equals(that.station) && date.equals(that.date);

	}

	@Override
	public int hashCode() {
		int result = station.hashCode();
		result = 31 * result + date.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "DailySummary{" +
				"station = " + station +
				",\n date = " + date +
				",\t meanTemperatureFahrenheit = " + meanTemperatureFahrenheit +
				",\t numMeanTempObservations = " + numMeanTempObservations +
				",\t dewPointFahrenheit = " + dewPointFahrenheit +
				",\t numDewPointObservations = " + numDewPointObservations +
				",\t seaLevelPressureMillibars = " + seaLevelPressureMillibars +
				",\t numSeaLevelPressureObservations = " + numSeaLevelPressureObservations +
				",\t stationPressureMillibars = " + stationPressureMillibars +
				",\t numStationPressureObservations = " + numStationPressureObservations +
				",\t visibilityMiles = " + visibilityMiles +
				",\t numVisibilityObservations = " + numVisibilityObservations +
				",\t meanWindSpeedKnots = " + meanWindSpeedKnots +
				",\t numMeanWindSpeedObservations = " + numMeanWindSpeedObservations +
				",\t maxWindSpeedKnots = " + maxWindSpeedKnots +
				",\t maxWindGustKnots = " + maxWindGustKnots +
				",\t maxTemperatureFahrenheit = " + maxTemperatureFahrenheit +
				",\t maxTempIsFromHourlyData = " + maxTempIsFromHourlyData +
				",\t minTemperatureFahrenheit = " + minTemperatureFahrenheit +
				",\t minTempIsFromHourlyData = " + minTempIsFromHourlyData +
				",\t precipitationInches = " + precipitationInches +
				",\t precipReportFlag = " + precipReportFlag +
				",\t snowDepth = " + snowDepth +
				",\t fogReported = " + fogReported +
				",\t rainOrDrizzleReported = " + rainOrDrizzleReported +
				",\t snowOrIcePelletsReported = " + snowOrIcePelletsReported +
				",\t hailReported = " + hailReported +
				",\t thunderReported = " + thunderReported +
				",\t tornadoReported = " + tornadoReported +
				"}\n\n";
	}
}
