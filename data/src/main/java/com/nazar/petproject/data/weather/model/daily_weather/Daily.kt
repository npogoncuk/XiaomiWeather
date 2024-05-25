package com.nazar.petproject.data.weather.model.daily_weather


import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeatherValues
import com.nazar.petproject.domain.weather.entities.daily_weather.IOneDayWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.defaultToString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.seconds

@Serializable
internal data class Daily(
    @SerialName("time")
    val time: List<String>,
    @SerialName("weather_code")
    val weatherCode: List<Int>,
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double>,
    @SerialName("apparent_temperature_max")
    val apparentTemperatureMax: List<Double>,
    @SerialName("apparent_temperature_min")
    val apparentTemperatureMin: List<Double>,
    @SerialName("sunrise")
    val sunrise: List<String>,
    @SerialName("sunset")
    val sunset: List<String>,
    @SerialName("daylight_duration")
    val daylightDuration: List<Double>,
    @SerialName("sunshine_duration")
    val sunshineDuration: List<Double>,
    @SerialName("uv_index_max")
    val uvIndexMax: List<Double>,
    @SerialName("uv_index_clear_sky_max")
    val uvIndexClearSkyMax: List<Double>,
    @SerialName("precipitation_sum")
    val precipitationSum: List<Double>,
    @SerialName("rain_sum")
    val rainSum: List<Double>,
    @SerialName("showers_sum")
    val showersSum: List<Double>,
    @SerialName("snowfall_sum")
    val snowfallSum: List<Double>,
    @SerialName("precipitation_hours")
    val precipitationHours: List<Double>,
    @SerialName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int>,
    @SerialName("wind_speed_10m_max")
    val windSpeed10mMax: List<Double>,
    @SerialName("wind_gusts_10m_max")
    val windGusts10mMax: List<Double>,
    @SerialName("wind_direction_10m_dominant")
    val windDirection10mDominant: List<Int>,
    @SerialName("shortwave_radiation_sum")
    val shortwaveRadiationSum: List<Double>
) : IDailyWeatherValues {

    override val oneDayWeatherList: List<IOneDayWeather>
        get() = time.indices.map { index ->
            object : IOneDayWeather {
                override val date
                    get() = LocalDate.parse(time[index], DateTimeFormatter.ISO_DATE)
                override val weatherCode = this@Daily.weatherCode[index]
                override val temperatureMax = temperature2mMax[index].toInt()
                override val temperatureMin = temperature2mMin[index].toInt()
                override val apparentTemperatureMax =
                    this@Daily.apparentTemperatureMax[index].toInt()
                override val apparentTemperatureMin =
                    this@Daily.apparentTemperatureMin[index].toInt()
                override val sunriseTime
                    get() = LocalDateTime.parse(sunrise[index], DateTimeFormatter.ISO_DATE_TIME)
                override val sunsetTime
                    get() = LocalDateTime.parse(sunset[index], DateTimeFormatter.ISO_DATE_TIME)
                override val daylightDuration = this@Daily.daylightDuration[index].seconds
                override val sunshineDuration = this@Daily.sunshineDuration[index].seconds
                override val uvIndexMax = this@Daily.uvIndexMax[index].toInt()
                override val precipitationSum = this@Daily.precipitationSum[index].toInt()
                override val rainSum = this@Daily.rainSum[index].toInt()
                override val showersSum = this@Daily.showersSum[index].toInt()
                override val snowfallSum = this@Daily.snowfallSum[index].toInt()
                override val precipitationHours = this@Daily.precipitationHours[index]
                override val precipitationProbability = precipitationProbabilityMax[index]
                override val windSpeed = windSpeed10mMax[index]
                override val windGusts = windGusts10mMax[index]
                override val windDirection = windDirection10mDominant[index]

                override fun toString(): String = this.defaultToString()
            }
        }
}