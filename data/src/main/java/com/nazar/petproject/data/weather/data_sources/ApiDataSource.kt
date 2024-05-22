package com.nazar.petproject.data.weather.data_sources

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.exceptions.ApiCallException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import javax.inject.Inject

private const val  CURRENT_WEATHER_PARAMETERS = "temperature_m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
internal class ApiDataSource @Inject constructor(private val httpClient: HttpClient) : WeatherDataSource {

    override suspend fun getCurrentWeather(): IResult<CurrentWeather> {
        val response: HttpResponse = httpClient.get {
            addLatitudeLongitude()
            addComaSeparatedWeatherParameters("current", CURRENT_WEATHER_PARAMETERS)
            parameter("timezone", "Europe/London")
        }

        return if (response.status.isSuccess()) {
            IResult.Success(response.body())
        } else {
            IResult.Error(exception = ApiCallException(response.bodyAsText()))
        }
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