package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.weather.model.current_weather.ICurrentWeather

interface CurrentWeatherUseCase {

    suspend fun getCurrentWeather(): ICurrentWeather

    class Base (private val weatherRepository: WeatherRepository) : CurrentWeatherUseCase {

        override suspend fun getCurrentWeather(): ICurrentWeather = weatherRepository.getCurrentWeather()

    }
}