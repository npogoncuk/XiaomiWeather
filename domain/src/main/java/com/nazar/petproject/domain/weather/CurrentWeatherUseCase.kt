package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherUseCase {

    suspend fun getCurrentWeather(): Flow<IResult<ICurrentWeather>>

    class Base (private val weatherRepository: WeatherRepository) : CurrentWeatherUseCase {

        override suspend fun getCurrentWeather(): Flow<IResult<ICurrentWeather>> = weatherRepository.getCurrentWeather()

    }
}