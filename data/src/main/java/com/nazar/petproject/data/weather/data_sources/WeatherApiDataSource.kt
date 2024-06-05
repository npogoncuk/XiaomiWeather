package com.nazar.petproject.data.weather.data_sources

import com.nazar.petproject.data.settings.units_list_storage.TemperatureUnits
import com.nazar.petproject.data.settings.units_list_storage.WindUnits
import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.nazar.petproject.data.weather.model.daily_weather.DailyWeather
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.exceptions.ApiCallException
import com.nazar.petproject.domain.exceptions.DeveloperMistakeException
import com.nazar.petproject.domain.exceptions.NoInternetException
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.isSame
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import javax.inject.Inject

private const val CURRENT_WEATHER_PARAMETERS = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
private const val DAILY_WEATHER_PARAMETERS = "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,daylight_duration,sunshine_duration,uv_index_max,uv_index_clear_sky_max,precipitation_sum,rain_sum,showers_sum,snowfall_sum,precipitation_hours,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum"

internal class WeatherApiDataSource @Inject constructor(private val httpClient: HttpClient) : WeatherDataSource {

    override suspend fun getCurrentWeather(
        temperatureUnit: MeasurementUnit,
        windSpeedUnit: MeasurementUnit,
    ): IResult<CurrentWeather> {
        val response = httpClient.getApiResponse<CurrentWeather> {
            addLatitudeLongitude()
            addComaSeparatedWeatherParameters("current", CURRENT_WEATHER_PARAMETERS)
            addKievTimeZone()
            addTemperatureAndWindSpeedUnits(temperatureUnit, windSpeedUnit)
        }
        return response.handleSuccessErrorException()
    }

    override suspend fun getDailyWeather(): IResult<IDailyWeather> {
        val response = httpClient.getApiResponse<DailyWeather> {
            addLatitudeLongitude()
            addComaSeparatedWeatherParameters("daily", DAILY_WEATHER_PARAMETERS)
            addKievTimeZone()
        }
        return response.handleSuccessErrorException()
    }
}

private fun HttpRequestBuilder.addLatitudeLongitude(latitude: Double = 49.842957, longitude: Double = 24.031111) {
    parameter("latitude", latitude)
    parameter("longitude", longitude)
}

private fun HttpRequestBuilder.addKievTimeZone() {
    parameter("timezone", "Europe/Kiev")
}

private fun HttpRequestBuilder.addComaSeparatedWeatherParameters(key: String, params: String) {
    params.split(",").forEach { parameter ->
        parameter(key, parameter)

    }
}

private fun HttpRequestBuilder.addTemperatureAndWindSpeedUnits(temperatureUnit: MeasurementUnit, windSpeedUnit: MeasurementUnit) {
    // no need to add any parameters if the temperature unit is the same as the default one
    if (temperatureUnit isSame TemperatureUnits.Fahrenheit) {
        parameter("temperature_unit", "fahrenheit")
    }

    val windSpeedUnitKey = "wind_speed_unit"
    when(windSpeedUnit) {
        WindUnits.MilesPerHour -> parameter(windSpeedUnitKey, "mph")
        WindUnits.MetersPerSecond -> parameter(windSpeedUnitKey, "ms")
        WindUnits.Knots -> parameter(windSpeedUnitKey, "kn")
    }
}

private fun<T> ApiResponse<T>.handleSuccessErrorException(): IResult<T> {
    var result: IResult<T>? = null
    this.onSuccess {
        result = IResult.Success(data)
    }.onError {
        result = IResult.Error(exception = DeveloperMistakeException(), message = this.messageOrNull)
    }.onException {
        result = IResult.Error(exception = throwable.requestThrowableToDomainException())
    }
    return result!!
}

private fun Throwable.requestThrowableToDomainException() = when(this) {
    is ClientRequestException, is ServerResponseException, is SerializationException -> {
        DeveloperMistakeException()
    }
    is IOException -> NoInternetException()
    else -> ApiCallException(this.message ?: "Unknown error")
}