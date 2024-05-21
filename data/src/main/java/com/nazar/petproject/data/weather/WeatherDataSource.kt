package com.nazar.petproject.data.weather

import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.nazar.petproject.domain.weather.model.ICurrentWeather


internal interface WeatherDataSource {

    suspend fun getCurrentWeather(): CurrentWeather
}