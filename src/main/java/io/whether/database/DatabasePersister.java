package io.whether.database;

import io.whether.domain.DailySummary;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public interface DatabasePersister {

	@SqlUpdate("create unlogged table stations (" +
			"       station_id      integer not null," +
			"       wban_id         integer not null," +
			"       station_name    varchar," +
			"       country_id      varchar," +
			"       us_state        varchar," +
			"       latitude        numeric(6,3)," +
			"       longitude       numeric(6,3)," +
			"       elevation       numeric(5,1)," +
			"       constraint pk_station_ids primary key(station_id,wban_id)" +
			");")
	void createStationTable();


	@SqlUpdate("create unlogged table daily_summaries (" +
			"station_id integer not null," +
			"wban_id integer not null," +

			"pk serial primary key, " +
			"summary_date date not null," +

			"mean_temp_fahr numeric(5,1)," +
			"num_mean_temp_obs integer," +

			"max_temp_fahr numeric(5,1)," +
			"max_temp_from_hourly boolean," +

			"min_temp_fahr numeric(5,1)," +
			"min_temp_from_hourly boolean," +

			"dew_point_fahr numeric(5,1)," +
			"num_dew_point_obs integer," +

			"sea_pressure_millibar numeric(5,1)," +
			"num_sea_pressure_obs integer," +

			"station_pressure_millibar numeric(5,1)," +
			"num_stat_pressure_obs integer," +

			"visibility_miles numeric(4,1)," +
			"num_visibility_obs integer," +

			"mean_wind_speed_knots numeric(4,1)," +
			"num_mean_wind_spd_obs integer," +

			"max_wind_speed_knots numeric(4,1)," +

			"max_wing_gust_knots numeric(4,1)," +

			"precip_inches  numeric(4,2)," +
			"precip_report_flag character(1)," +

			"snow_depth_inches numeric(4,1)," +

			"fog_reported boolean," +
			"rain_reported boolean," +
			"snow_reported boolean," +
			"hail_reported boolean," +
			"thunder_reported boolean," +
			"tornado_reported boolean," +

			"constraint station_pk foreign key(station_id,wban_id) references stations(station_id,wban_id)" +

			");")
	void createSummaryTable();

	@SqlUpdate("create index station_summary_index on daily_summaries (station_id,wban_id,summary_date)")
	void createSummaryIndex();

	@SqlUpdate("insert into stations " +
			"(" +
			"station_id, " +
			"wban_id, " +
			"station_name, " +
			"country_id, " +
			"us_state, " +
			"latitude, " +
			"longitude, " +
			"elevation" +
			")" +

			"values (" +
			":stationID, " +
			":wbanID, " +
			":stationName, " +
			":countryID, " +
			":state, " +
			":latitude, " +
			":longitude, " +
			":elevation" +
			")")
	void insertStationRecord(@Bind("stationID") int stationID,
							 @Bind("wbanID") int wbanID,
							 @Bind("stationName") String stationName,
							 @Bind("countryID") String countryID,
							 @Bind("state") String state,
							 @Bind("latitude") Double latitude,
							 @Bind("longitude") Double longitude,
							 @Bind("elevation") Double elevation);

	@SqlBatch(
			"insert into daily_summaries" +
					"(" +
					"station_id, " +
					"wban_id, " +
					"summary_date, " +
					"mean_temp_fahr, " +
					"num_mean_temp_obs, " +
					"max_temp_fahr, " +
					"max_temp_from_hourly, " +
					"min_temp_fahr, " +
					"min_temp_from_hourly, " +
					"dew_point_fahr, " +
					"num_dew_point_obs, " +
					"sea_pressure_millibar, " +
					"num_sea_pressure_obs, " +
					"station_pressure_millibar, " +
					"num_stat_pressure_obs, " +
					"visibility_miles, " +
					"num_visibility_obs, " +
					"mean_wind_speed_knots, " +
					"num_mean_wind_spd_obs, " +
					"max_wind_speed_knots, " +
					"max_wing_gust_knots, " +
					"precip_inches, " +
					"precip_report_flag, " +
					"snow_depth_inches, " +
					"fog_reported, " +
					"rain_reported, " +
					"snow_reported, " +
					"hail_reported, " +
					"thunder_reported, " +
					"tornado_reported" +
					") " +

					"values (" +
					":stationID," +
					":wbanID, " +
					":date, " +
					":meanTemperatureFahrenheit, " +
					":numMeanTempObservations, " +
					":maxTemperatureFahrenheit, " +
					":maxTempIsFromHourlyData, " +
					":minTemperatureFahrenheit, " +
					":minTempIsFromHourlyData, " +
					":dewPointFahrenheit, " +
					":numDewPointObservations, " +
					":seaLevelPressureMillibars, " +
					":numSeaLevelPressureObservations, " +
					":stationPressureMillibars, " +
					":numStationPressureObservations, " +
					":visibilityMiles, " +
					":numVisibilityObservations, " +
					":meanWindSpeedKnots, " +
					":numMeanWindSpeedObservations, " +
					":maxWindSpeedKnots, " +
					":maxWindGustKnots, " +
					":precipitationInches, " +
					":precipReportFlag, " +
					":snowDepth, " +
					":fogReported, " +
					":rainOrDrizzleReported, " +
					":snowOrIcePelletsReported, " +
					":hailReported, " +
					":thunderReported, " +
					":tornadoReported" +
					")")
	void insertSummaryRecord(@BindDailySummary List<DailySummary> summaries);

	void close();
}
