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
    int numMaxWindSpeedObservations;

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

    public DailySummary(Station station, LocalDate date) {
        this.station = station;
        this.date = date;
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

    public void setMeanTemperatureFahrenheit(Double meanTemperatureFahrenheit, int numMeanTempObservations) {
        this.meanTemperatureFahrenheit = meanTemperatureFahrenheit;
        this.numMeanTempObservations = numMeanTempObservations;
    }

    public int getNumMeanTempObservations() {
        return numMeanTempObservations;
    }

    public Double getDewPointFahrenheit() {
        return dewPointFahrenheit;
    }

    public void setDewPointFahrenheit(Double dewPointFahrenheit, int numDewPointObservations) {
        this.dewPointFahrenheit = dewPointFahrenheit;
        this.numDewPointObservations = numDewPointObservations;
    }

    public int getNumDewPointObservations() {
        return numDewPointObservations;
    }

    public Double getSeaLevelPressureMillibars() {
        return seaLevelPressureMillibars;
    }

    public void setSeaLevelPressureMillibars(Double seaLevelPressureMillibars, int numSeaLevelPressureObservations) {
        this.seaLevelPressureMillibars = seaLevelPressureMillibars;
        this.numSeaLevelPressureObservations = numSeaLevelPressureObservations;
    }

    public int getNumSeaLevelPressureObservations() {
        return numSeaLevelPressureObservations;
    }

    public Double getStationPressureMillibars() {
        return stationPressureMillibars;
    }

    public void setStationPressureMillibars(Double stationPressureMillibars, int numStationPressureObservations) {
        this.stationPressureMillibars = stationPressureMillibars;
        this.numStationPressureObservations = numStationPressureObservations;
    }

    public int getNumStationPressureObservations() {
        return numStationPressureObservations;
    }

    public Double getVisibilityMiles() {
        return visibilityMiles;
    }

    public void setVisibilityMiles(Double visibilityMiles, int numVisibilityObservations) {
        this.visibilityMiles = visibilityMiles;
        this.numVisibilityObservations = numVisibilityObservations;
    }

    public int getNumVisibilityObservations() {
        return numVisibilityObservations;
    }

    public Double getMeanWindSpeedKnots() {
        return meanWindSpeedKnots;
    }

    public void setMeanWindSpeedKnots(Double meanWindSpeedKnots, int numMeanWindSpeedObservations) {
        this.meanWindSpeedKnots = meanWindSpeedKnots;
        this.numMeanWindSpeedObservations = numMeanWindSpeedObservations;
    }

    public int getNumMeanWindSpeedObservations() {
        return numMeanWindSpeedObservations;
    }

    public Double getMaxWindSpeedKnots() {
        return maxWindSpeedKnots;
    }

    public void setMaxWindSpeedKnots(Double maxWindSpeedKnots) {
        this.maxWindSpeedKnots = maxWindSpeedKnots;
    }

    public int getNumMaxWindSpeedObservations() {
        return numMaxWindSpeedObservations;
    }

    public void setNumMaxWindSpeedObservations(int numMaxWindSpeedObservations) {
        this.numMaxWindSpeedObservations = numMaxWindSpeedObservations;
    }

    public Double getMaxWindGustKnots() {
        return maxWindGustKnots;
    }

    public void setMaxWindGustKnots(Double maxWindGustKnots) {
        this.maxWindGustKnots = maxWindGustKnots;
    }

    public Double getMaxTemperatureFahrenheit() {
        return maxTemperatureFahrenheit;
    }

    public void setMaxTemperatureFahrenheit(Double maxTemperatureFahrenheit, boolean maxTempIsFromHourlyData) {
        this.maxTemperatureFahrenheit = maxTemperatureFahrenheit;
        this.maxTempIsFromHourlyData = maxTempIsFromHourlyData;
    }

    public boolean isMaxTempIsFromHourlyData() {
        return maxTempIsFromHourlyData;
    }

    public Double getMinTemperatureFahrenheit() {
        return minTemperatureFahrenheit;
    }

    public void setMinTemperatureFahrenheit(Double minTemperatureFahrenheit, boolean minTempIsFromHourlyData) {
        this.minTemperatureFahrenheit = minTemperatureFahrenheit;
        this.minTempIsFromHourlyData = minTempIsFromHourlyData;
    }

    public boolean isMinTempIsFromHourlyData() {
        return minTempIsFromHourlyData;
    }

    public Double getPrecipitationInches() {
        return precipitationInches;
    }

    public void setPrecipitationInches(Double precipitationInches, String precipReportFlag) {
        this.precipitationInches = precipitationInches;
        this.precipReportFlag = precipReportFlag;
    }

    public String getPrecipReportFlag() {
        return precipReportFlag;
    }

    public Double getSnowDepth() {
        return snowDepth;
    }

    public void setSnowDepth(Double snowDepth) {
        this.snowDepth = snowDepth;
    }

    public boolean isFogReported() {
        return fogReported;
    }

    public void setFogReported(boolean fogReported) {
        this.fogReported = fogReported;
    }

    public boolean isRainOrDrizzleReported() {
        return rainOrDrizzleReported;
    }

    public void setRainOrDrizzleReported(boolean rainOrDrizzleReported) {
        this.rainOrDrizzleReported = rainOrDrizzleReported;
    }

    public boolean isSnowOrIcePelletsReported() {
        return snowOrIcePelletsReported;
    }

    public void setSnowOrIcePelletsReported(boolean snowOrIcePelletsReported) {
        this.snowOrIcePelletsReported = snowOrIcePelletsReported;
    }

    public boolean isHailReported() {
        return hailReported;
    }

    public void setHailReported(boolean hailReported) {
        this.hailReported = hailReported;
    }

    public boolean isThunderReported() {
        return thunderReported;
    }

    public void setThunderReported(boolean thunderReported) {
        this.thunderReported = thunderReported;
    }

    public boolean isTornadoReported() {
        return tornadoReported;
    }

    public void setTornadoReported(boolean tornadoReported) {
        this.tornadoReported = tornadoReported;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
                ",\t numMaxWindSpeedObservations = " + numMaxWindSpeedObservations +
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
