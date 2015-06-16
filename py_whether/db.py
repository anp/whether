__author__ = 'adam'


def create_schema(cursor):
    station_table_statement = """
    CREATE TABLE stations (
       station_id      INTEGER NOT NULL,
       wban_id         INTEGER NOT NULL,
       station_name    VARCHAR,
       country_id      VARCHAR,
       us_state        VARCHAR,
       latitude        NUMERIC(6,3),
       longitude       NUMERIC(6,3),
       elevation       NUMERIC(5,1),
       CONSTRAINT pk_station_ids PRIMARY KEY(station_id,wban_id)
    );
    """

    summary_table_statement = """
    CREATE TABLE daily_summaries (
        station_id INTEGER NOT NULL,
        wban_id INTEGER NOT NULL,

        pk SERIAL PRIMARY KEY,
        summary_date DATE NOT NULL,

        mean_temp_fahr NUMERIC(5,1),
        num_mean_temp_obs INTEGER,

        max_temp_fahr NUMERIC(5,1),
        max_temp_from_hourly BOOLEAN,

        min_temp_fahr NUMERIC(5,1),
        min_temp_from_hourly BOOLEAN,

        dew_point_fahr NUMERIC(5,1),
        num_dew_point_obs INTEGER,

        sea_pressure_millibar NUMERIC(5,1),
        num_sea_pressure_obs INTEGER,

        station_pressure_millibar NUMERIC(5,1),
        num_stat_pressure_obs INTEGER,

        visibility_miles NUMERIC(4,1),
        num_visibility_obs INTEGER,

        mean_wind_speed_knots NUMERIC(4,1),
        num_mean_wind_spd_obs INTEGER,

        max_wind_speed_knots NUMERIC(4,1),

        max_wing_gust_knots NUMERIC(4,1),

        precip_inches  NUMERIC(4,2),
        precip_report_flag CHARACTER(1),

        snow_depth_inches NUMERIC(4,1),

        fog_reported BOOLEAN,
        rain_reported BOOLEAN,
        snow_reported BOOLEAN,
        hail_reported BOOLEAN,
        thunder_reported BOOLEAN,
        tornado_reported BOOLEAN,
        CONSTRAINT station_pk FOREIGN KEY(station_id,wban_id) REFERENCES stations(station_id,wban_id)

    );
    """

    cursor.execute(station_table_statement)
    cursor.execute(summary_table_statement)


def insert_station_record(cursor, station_id, wban_id, station_name, country_id, state, latitude, longitude, elevation):
    station_insert_statement = """
    insert into stations
    (
        station_id,
        wban_id,
        station_name,
        country_id,
        us_state,
        latitude,
        longitude,
        elevation
    )

    values (
        %(station_id)s,
        %(wban_id)s,
        %(station_name)s,
        %(country_id)s,
        %(state)s,
        %(latitude)s,
        %(longitude)s,
        %(elevation)s
    )
    """

    station_dict = {
        'station_id': station_id,
        'wban_id': wban_id,
        'station_name': station_name,
        'country_id': country_id,
        'state': state,
        'latitude': latitude,
        'longitude': longitude,
        'elevation': elevation
    }

    cursor.execute(station_insert_statement, station_dict)


def insert_summary_record(cursor,
                          station_id,
                          wban_id,
                          date,
                          mean_temp,
                          num_temp_obs,
                          max_temp,
                          max_t_from_hourly,
                          min_temp,
                          min_t_from_hourly,
                          dew_point,
                          num_dp_obs,
                          sea_pressure,
                          num_seapressure_obs,
                          station_pressure,
                          num_statpressure_obs,
                          visibility,
                          num_visi_obs,
                          mean_wind_speed,
                          num_windspd_obs,
                          max_wind_speed,
                          max_wind_gust,
                          precip,
                          precip_flag,
                          snow_depth,
                          fog,
                          rain,
                          snow,
                          hail,
                          thunder,
                          tornado):
    summary_insert_statement = """
    insert into daily_summaries
    (
        station_id,
        wban_id,
        summary_date,
        mean_temp_fahr,
        num_mean_temp_obs,
        max_temp_fahr,
        max_temp_from_hourly,
        min_temp_fahr,
        min_temp_from_hourly,
        dew_point_fahr,
        num_dew_point_obs,
        sea_pressure_millibar,
        num_sea_pressure_obs,
        station_pressure_millibar,
        num_stat_pressure_obs,
        visibility_miles,
        num_visibility_obs,
        mean_wind_speed_knots,
        num_mean_wind_spd_obs,
        max_wind_speed_knots,
        max_wing_gust_knots,
        precip_inches,
        precip_report_flag,
        snow_depth_inches,
        fog_reported,
        rain_reported,
        snow_reported,
        hail_reported,
        thunder_reported,
        tornado_reported
    )

    values (
        %(station_id)s,
        %(wban_id)s,
        %(date)s,
        %(mean_temp)s,
        %(num_temp_obs)s,
        %(max_temp)s,
        %(max_t_from_hourly)s,
        %(min_temp)s,
        %(min_t_from_hourly)s,
        %(dew_point)s,
        %(num_dp_obs)s,
        %(sea_pressure)s,
        %(num_sep_obs)s,
        %(station_pressure)s,
        %(num_stp_obs)s,
        %(visibility)s,
        %(num_visibility_obs)s,
        %(mean_wind_speed)s,
        %(num_windspd_obs)s,
        %(max_wind_speed)s,
        %(max_wind_gust)s,
        %(precip)s,
        %(precip_flag)s,
        %(snow_depth)s,
        %(fog_reported)s,
        %(rain_reported)s,
        %(snow_reported)s,
        %(hail_reported)s,
        %(thunder_reported)s,
        %(tornado_reported)s
    )
    """

    cursor.execute(summary_insert_statement, {
        'station_id': station_id,
        'wban_id': wban_id,
        'date': date,
        'mean_temp': mean_temp,
        'num_temp_obs': num_temp_obs,
        'max_temp': max_temp,
        'max_t_from_hourly': max_t_from_hourly,
        'min_temp': min_temp,
        'min_t_from_hourly': min_t_from_hourly,
        'dew_point': dew_point,
        'num_dp_obs': num_dp_obs,
        'sea_pressure': sea_pressure,
        'num_sep_obs': num_seapressure_obs,
        'station_pressure': station_pressure,
        'num_stp_obs': num_statpressure_obs,
        'visibility': visibility,
        'num_visibility_obs': num_visi_obs,
        'mean_wind_speed': mean_wind_speed,
        'num_windspd_obs': num_windspd_obs,
        'max_wind_speed': max_wind_speed,
        'max_wind_gust': max_wind_gust,
        'precip': precip,
        'precip_flag': precip_flag,
        'snow_depth': snow_depth,
        'fog_reported': fog,
        'rain_reported': rain,
        'snow_reported': snow,
        'hail_reported': hail,
        'thunder_reported': thunder,
        'tornado_reported': tornado
    })


def database_cleanup_and_index(cursor):
    index_statement = """
    CREATE INDEX station_summary_index ON daily_summaries (station_id,wban_id,summary_date)
    """

    cursor.execute(index_statement)
    cursor.execute("analyze")
