package com.nazar.petproject.data.weather.model.daily_weather


import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeatherUnits
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DailyUnits(
    @SerialName("time")
    val time: String,
    @SerialName("weather_code")
    val weatherCode: String,
    @SerialName("temperature_2m_max")
    val temperature2mMax: String,
    @SerialName("temperature_2m_min")
    val temperature2mMin: String,
    @SerialName("apparent_temperature_max")
    val apparentTemperatureMax: String,
    @SerialName("apparent_temperature_min")
    val apparentTemperatureMin: String,
    @SerialName("sunrise")
    val sunrise: String,
    @SerialName("sunset")
    val sunset: String,
    @SerialName("daylight_duration")
    val daylightDuration: String,
    @SerialName("sunshine_duration")
    val sunshineDuration: String,
    @SerialName("uv_index_max")
    val uvIndexMax: String,
    @SerialName("uv_index_clear_sky_max")
    val uvIndexClearSkyMax: String,
    @SerialName("precipitation_sum")
    val precipitationSum: String,
    @SerialName("rain_sum")
    val rainSum: String,
    @SerialName("showers_sum")
    val showersSum: String,
    @SerialName("snowfall_sum")
    val snowfallSum: String,
    @SerialName("precipitation_hours")
    val precipitationHours: String,
    @SerialName("precipitation_probability_max")
    val precipitationProbabilityMax: String,
    @SerialName("wind_speed_10m_max")
    val windSpeed10mMax: String,
    @SerialName("wind_gusts_10m_max")
    val windGusts10mMax: String,
    @SerialName("wind_direction_10m_dominant")
    val windDirection10mDominant: String,
    @SerialName("shortwave_radiation_sum")
    val shortwaveRadiationSum: String
) : IDailyWeatherUnits