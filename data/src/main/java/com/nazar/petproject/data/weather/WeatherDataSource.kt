package com.nazar.petproject.data.weather

import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.nazar.petproject.domain.weather.model.ICurrentWeather


interface WeatherDataSource {

    suspend fun getCurrentWeather(): ICurrentWeather
}