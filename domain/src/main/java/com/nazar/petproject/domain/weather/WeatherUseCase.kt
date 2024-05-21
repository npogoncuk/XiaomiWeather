package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.weather.model.ICurrentWeather

interface WeatherUseCase {

    suspend fun getCurrentWeather(): ICurrentWeather

    class Base (private val weatherRepository: WeatherRepository) : WeatherUseCase {

        override suspend fun getCurrentWeather(): ICurrentWeather = weatherRepository.getCurrentWeather()

    }
}