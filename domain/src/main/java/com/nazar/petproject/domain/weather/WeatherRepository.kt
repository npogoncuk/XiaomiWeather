package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.weather.model.IAllWeather

interface WeatherRepository {

    suspend fun getAllWeather(): IAllWeather
}