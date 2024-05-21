package com.nazar.petproject.data.weather.data_sources

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.nazar.petproject.domain.weather.model.ICurrentWeather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

private const val BASE_URL = "https://api.open-meteo.com/v1/dwd-icon"
private const val query = "?latitude=52.52&longitude=13.41&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m&hourly=temperature_2m,weather_code,wind_speed_10m&daily=weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,precipitation_probability_max,wind_direction_10m_dominant&timezone=Europe%2FLondon&past_days=1&forecast_days=5"
internal class ApiDataSource @Inject constructor(private val httpClient: HttpClient) : WeatherDataSource {

    override suspend fun getCurrentWeather(): CurrentWeather {
        val response: HttpResponse = httpClient.get("$BASE_URL$query")
        return response.body()!!
    }

}