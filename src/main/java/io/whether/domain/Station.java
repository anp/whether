package io.whether.domain;

public class Station {
    int usafID;
    int wbanID;

    String stationName;
    String countryID;
    String state;
    String icaoID;

    float latitude;
    float longitude;
    float elevation;

    public Station(int usafID, int wbanID) {
        this.usafID = usafID;
        this.wbanID = wbanID;
    }

    public Station(int usafID, int wbanID, float latitude, float longitude, float elevation) {
        this.usafID = usafID;
        this.wbanID = wbanID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    public int getUsafID() {
        return usafID;
    }

    public void setUsafID(int usafID) {
        this.usafID = usafID;
    }

    public int getWbanID() {
        return wbanID;
    }

    public void setWbanID(int wbanID) {
        this.wbanID = wbanID;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIcaoID() {
        return icaoID;
    }

    public void setIcaoID(String icaoID) {
        this.icaoID = icaoID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        return getUsafID() == station.getUsafID() && getWbanID() == station.getWbanID();

    }

    @Override
    public int hashCode() {
        int result = getUsafID();
        result = 31 * result + getWbanID();
        return result;
    }

    @Override
    public String toString() {
        return "Station{" +
                "usafID=" + usafID +
                ", wbanID=" + wbanID +
                ", stationName='" + stationName + '\'' +
                ", countryID='" + countryID + '\'' +
                ", state='" + state + '\'' +
                ", icaoID='" + icaoID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                '}';
    }
}
