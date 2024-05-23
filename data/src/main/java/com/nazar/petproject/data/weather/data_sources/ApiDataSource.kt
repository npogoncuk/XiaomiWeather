package com.nazar.petproject.data.weather.data_sources

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.exceptions.ApiCallException
import com.nazar.petproject.domain.exceptions.DeveloperMistakeException
import com.nazar.petproject.domain.exceptions.NoInternetException
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
import io.ktor.client.request.parameter
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import javax.inject.Inject

private const val  CURRENT_WEATHER_PARAMETERS = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"

internal class ApiDataSource @Inject constructor(private val httpClient: HttpClient) : WeatherDataSource {

    override suspend fun getCurrentWeather(): IResult<CurrentWeather> {
/*
        val response: HttpResponse = httpClient.get {
            addLatitudeLongitude()
            addComaSeparatedWeatherParameters("current", CURRENT_WEATHER_PARAMETERS)
            parameter("timezone", "Europe/London")
        }*/

        val response = httpClient.getApiResponse<CurrentWeather> {
            addLatitudeLongitude()
            addComaSeparatedWeatherParameters("current", CURRENT_WEATHER_PARAMETERS)
            parameter("timezone", "Europe/London")
        }
        return response.handleSuccessErrorException()
    }

}

private fun HttpRequestBuilder.addLatitudeLongitude(latitude: Double = 49.842957, longitude: Double = 24.031111) {
    parameter("latitude", latitude)
    parameter("longitude", longitude)
}

private fun HttpRequestBuilder.addComaSeparatedWeatherParameters(key: String, params: String) {
    params.split(",").forEach { parameter ->
        parameter(key, parameter)

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