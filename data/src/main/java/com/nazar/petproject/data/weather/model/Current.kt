package com.nazar.petproject.data.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("time")
    val time: String,
    @SerialName("interval")
    val interval: Int,
    @SerialName("temperature_2m")
    val temperature2m: Double,
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: Int,
    @SerialName("apparent_temperature")
    val apparentTemperature: Double,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("precipitation")
    val precipitation: Int,
    @SerialName("rain")
    val rain: Int,
    @SerialName("showers")
    val showers: Int,
    @SerialName("snowfall")
    val snowfall: Int,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("cloud_cover")
    val cloudCover: Int,
    @SerialName("surface_pressure")
    val surfacePressure: Double,
    @SerialName("wind_speed_10m")
    val windSpeed10m: Double,
    @SerialName("wind_direction_10m")
    val windDirection10m: Int,
    @SerialName("wind_gusts_10m")
    val windGusts10m: Double
)