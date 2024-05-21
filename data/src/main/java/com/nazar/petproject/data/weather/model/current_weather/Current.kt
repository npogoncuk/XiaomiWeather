package com.nazar.petproject.data.weather.model.current_weather


import com.nazar.petproject.domain.weather.model.ICurrent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Current(
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
    override val precipitation: Double,
    @SerialName("rain")
    override val rain: Double,
    @SerialName("showers")
    override val showers: Double,
    @SerialName("snowfall")
    val snowfall: Double,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("cloud_cover")
    val cloudCover: Int,
    @SerialName("pressure_msl")
    val pressureMsl: Double,
    @SerialName("surface_pressure")
    val surfacePressure: Double,
    @SerialName("wind_speed_10m")
    val windSpeed10m: Double,
    @SerialName("wind_direction_10m")
    val windDirection10m: Int,
    @SerialName("wind_gusts_10m")
    val windGusts10m: Double
) : ICurrent