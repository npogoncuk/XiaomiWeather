package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather


interface WeatherDataSource {

    suspend fun getCurrentWeather(): ICurrentWeather
}