package io.whether.domain;

public class Station {
	int stationID;
	int wbanID;

    String stationName;
    String countryID;
    String state;

	Double latitude;
	Double longitude;
	Double elevation;

	public Station(final int mainID, final int wban, final Double lat, final Double lng, final Double elev) {
		stationID = mainID;
		wbanID = wban;
		latitude = lat;
		longitude = lng;
		elevation = elev;
	}

	public int getStationID() {
		return stationID;
	}

    public int getWbanID() {
        return wbanID;
    }

    public String getStationName() {
        return stationName;
    }

	public void setStationName(final String newName) {
		this.stationName = newName;
	}

    public String getCountryID() {
        return countryID;
    }

	public void setCountryID(final String newCountry) {
		this.countryID = newCountry;
	}

    public String getState() {
        return state;
    }

	public void setState(final String newState) {
		this.state = newState;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

		if (stationID != station.stationID) return false;
		if (wbanID != station.wbanID) return false;
		if (stationName != null ? !stationName.equals(station.stationName) : station.stationName != null) return false;
		if (countryID != null ? !countryID.equals(station.countryID) : station.countryID != null) return false;
		if (state != null ? !state.equals(station.state) : station.state != null) return false;
		if (latitude != null ? !latitude.equals(station.latitude) : station.latitude != null) return false;
		if (longitude != null ? !longitude.equals(station.longitude) : station.longitude != null) return false;
		return !(elevation != null ? !elevation.equals(station.elevation) : station.elevation != null);

    }

    @Override
    public int hashCode() {
		int result = stationID;
		result = 31 * result + wbanID;
		result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
		result = 31 * result + (countryID != null ? countryID.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
		result = 31 * result + (elevation != null ? elevation.hashCode() : 0);
		return result;
	}

    @Override
    public String toString() {
        return "Station{" +
				"stationID=" + stationID +
				", wbanID=" + wbanID +
				", stationName='" + stationName + '\'' +
                ", countryID='" + countryID + '\'' +
                ", state='" + state + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                '}';
    }
}
