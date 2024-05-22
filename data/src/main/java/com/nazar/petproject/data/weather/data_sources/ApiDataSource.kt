package com.nazar.petproject.data.weather.data_sources

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrThrow
import com.skydoves.sandwich.ktor.requestApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

private const val  CURRENT_WEATHER_PARAMETERS = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
internal class ApiDataSource @Inject constructor(private val httpClient: HttpClient) : WeatherDataSource {

    override suspend fun getCurrentWeather(): CurrentWeather {
        val response: HttpResponse = httpClient.get {
            addLatitudeLongitude()
            addComaSeparatedWeatherParameters("current", CURRENT_WEATHER_PARAMETERS)
            parameter("timezone", "Europe/London")
        }
        val response1: ApiResponse<CurrentWeather> = httpClient.requestApiResponse {
            addLatitudeLongitude()
            addComaSeparatedWeatherParameters("current", CURRENT_WEATHER_PARAMETERS)
            parameter("timezone", "Europe/London")
        }

        return response.body() ?: response1.getOrThrow()
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