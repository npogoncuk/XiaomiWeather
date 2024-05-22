package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather

interface CurrentWeatherUseCase {

    suspend fun getCurrentWeather(): ICurrentWeather
    suspend fun getCurrentWeatherResult(): Result<ICurrentWeather>

    class Base (private val weatherRepository: WeatherRepository) : CurrentWeatherUseCase {

        override suspend fun getCurrentWeather(): ICurrentWeather = weatherRepository.getCurrentWeather()

        override suspend fun getCurrentWeatherResult(): Result<ICurrentWeather> = TODO()

    }
}