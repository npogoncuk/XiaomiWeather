package com.nazar.petproject.domain.weather


import com.nazar.petproject.domain.weather.model.ICurrentWeather

interface WeatherRepository {

    suspend fun getCurrentWeather(): ICurrentWeather
}