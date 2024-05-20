package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.weather.model.IAllWeather

interface AllWeatherUseCase {

    suspend fun getAllWeather(): IAllWeather

    class Base(private val weatherRepository: WeatherRepository) : AllWeatherUseCase {

        override suspend fun getAllWeather(): IAllWeather = weatherRepository.getAllWeather()

    }
}