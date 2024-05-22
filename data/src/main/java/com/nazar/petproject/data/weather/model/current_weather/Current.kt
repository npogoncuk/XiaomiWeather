package com.nazar.petproject.data.weather.model.current_weather


import com.nazar.petproject.domain.weather.model.current_weather.ICurrentWeatherValues
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Current(
    @SerialName("time")
    val time: String,
    @SerialName("interval")
    val interval: Int,
    @SerialName("temperature_2m")
    override val temperature2m: Double,
    @SerialName("relative_humidity_2m")
    override val relativeHumidity2m: Int,
    @SerialName("apparent_temperature")
    override val apparentTemperature: Double,
    @SerialName("is_day")
    val isDayInt: Int,
    @SerialName("precipitation")
    override val precipitation: Double,
    @SerialName("rain")
    override val rain: Double,
    @SerialName("showers")
    override val showers: Double,
    @SerialName("snowfall")
    override val snowfall: Double,
    @SerialName("weather_code")
    override val weatherCode: Int,
    @SerialName("cloud_cover")
    override val cloudCover: Int,
    @SerialName("pressure_msl")
    override val pressureMsl: Double,
    @SerialName("surface_pressure")
    override val surfacePressure: Double,
    @SerialName("wind_speed_10m")
    override val windSpeed10m: Double,
    @SerialName("wind_direction_10m")
    override val windDirection10m: Int,
    @SerialName("wind_gusts_10m")
    override val windGusts10m: Double
) : ICurrentWeatherValues {
    override val isDay: Boolean
        get() = this@Current.isDayInt == 1
}