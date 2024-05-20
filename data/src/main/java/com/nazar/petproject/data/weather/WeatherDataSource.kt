package com.nazar.petproject.data.weather

import com.nazar.petproject.data.weather.model.AllWeather

interface WeatherDataSource {

    suspend fun getAllWeather(): AllWeather
}