__author__ = 'adam'

station_create_table_statement = """
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

summary_create_table_statement = """
    CREATE TABLE daily_summaries (
        station_id INTEGER NOT NULL,
        wban_id INTEGER NOT NULL,

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

    values ( $1, $2, $3, $4, $5, $6, $7, $8 )
    """

summary_copy_statement = """
copy daily_summaries from STDIN WITH (NULL 'None')
"""

index_statement = """
    CREATE INDEX station_summary_index ON daily_summaries (station_id,wban_id,summary_date)
    """

analyze_statement = 'analyze'
