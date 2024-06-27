package com.nazar.petproject.data.weather.model.current_weather


import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeatherUnits
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentUnits(
    @SerialName("time")
    val time: String,
    @SerialName("interval")
    val interval: String,
    @SerialName("temperature_2m")
    val temperature2m: String,
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: String,
    @SerialName("apparent_temperature")
    val apparentTemperature: String,
    @SerialName("is_day")
    val isDay: String,
    @SerialName("precipitation")
    val precipitation: String,
    @SerialName("rain")
    val rain: String,
    @SerialName("showers")
    val showers: String,
    @SerialName("snowfall")
    val snowfall: String,
    @SerialName("weather_code")
    val weatherCode: String,
    @SerialName("cloud_cover")
    val cloudCover: String,
    @SerialName("pressure_msl")
    val pressureMsl: String,
    @SerialName("surface_pressure")
    val surfacePressure: String,
    @SerialName("wind_speed_10m")
    val windSpeed10m: String,
    @SerialName("wind_direction_10m")
    val windDirection10m: String,
    @SerialName("wind_gusts_10m")
    val windGusts10m: String
) : ICurrentWeatherUnits