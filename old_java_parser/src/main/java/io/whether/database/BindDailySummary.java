package io.whether.database;

import io.whether.domain.DailySummary;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

// our binding annotation
@BindingAnnotation(BindDailySummary.DailySummaryBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindDailySummary {

	class DailySummaryBinderFactory implements BinderFactory {
		public Binder build(Annotation annotation) {
			return new Binder<BindDailySummary, DailySummary>() {
				public void bind(SQLStatement q, BindDailySummary bind, DailySummary s) {
					q.bind("stationID", s.getStation().getStationID());
					q.bind("wbanID", s.getStation().getWbanID());
					q.bind("date", java.sql.Date.valueOf(s.getDate()));
					q.bind("meanTemperatureFahrenheit", s.getMeanTemperatureFahrenheit());
					q.bind("numMeanTempObservations", s.getNumMeanTempObservations());
					q.bind("maxTemperatureFahrenheit", s.getMaxTemperatureFahrenheit());
					q.bind("maxTempIsFromHourlyData", s.isMaxTempIsFromHourlyData());
					q.bind("minTemperatureFahrenheit", s.getMinTemperatureFahrenheit());
					q.bind("minTempIsFromHourlyData", s.isMinTempIsFromHourlyData());
					q.bind("dewPointFahrenheit", s.getDewPointFahrenheit());
					q.bind("numDewPointObservations", s.getNumDewPointObservations());
					q.bind("seaLevelPressureMillibars", s.getSeaLevelPressureMillibars());
					q.bind("numSeaLevelPressureObservations", s.getNumSeaLevelPressureObservations());
					q.bind("stationPressureMillibars", s.getStationPressureMillibars());
					q.bind("numStationPressureObservations", s.getNumStationPressureObservations());
					q.bind("visibilityMiles", s.getVisibilityMiles());
					q.bind("numVisibilityObservations", s.getNumVisibilityObservations());
					q.bind("meanWindSpeedKnots", s.getMeanWindSpeedKnots());
					q.bind("numMeanWindSpeedObservations", s.getNumMeanWindSpeedObservations());
					q.bind("maxWindSpeedKnots", s.getMaxWindSpeedKnots());
					q.bind("maxWindGustKnots", s.getMaxWindGustKnots());
					q.bind("precipitationInches", s.getPrecipitationInches());
					q.bind("precipReportFlag", s.getPrecipReportFlag());
					q.bind("snowDepth", s.getSnowDepth());
					q.bind("fogReported", s.isFogReported());
					q.bind("rainOrDrizzleReported", s.isRainOrDrizzleReported());
					q.bind("snowOrIcePelletsReported", s.isSnowOrIcePelletsReported());
					q.bind("hailReported", s.isHailReported());
					q.bind("thunderReported", s.isThunderReported());
					q.bind("tornadoReported", s.isTornadoReported());
				}
			};
		}
	}
}
