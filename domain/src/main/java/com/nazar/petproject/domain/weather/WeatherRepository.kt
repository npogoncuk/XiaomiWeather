package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather


interface WeatherRepository {

    suspend fun getCurrentWeather(): ICurrentWeather
}