package com.nazar.petproject.data.weather.data_sources

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

private const val BASE_URL = "https://api.open-meteo.com/v1/"
private const val query = "forecast?latitude=52.52&longitude=13.41&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m&timezone=Europe%2FLondon&forecast_days=1"
internal class ApiDataSource @Inject constructor(private val httpClient: HttpClient) : WeatherDataSource {

    override suspend fun getCurrentWeather(): CurrentWeather {
        val response: HttpResponse = httpClient.get("$BASE_URL$query")

        return response.body()!!
    }

}