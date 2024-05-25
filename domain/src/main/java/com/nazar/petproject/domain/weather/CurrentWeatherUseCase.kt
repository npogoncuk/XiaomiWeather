package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherUseCase {

    suspend operator fun invoke(): Flow<IResult<ICurrentWeather>>

    class Base (private val weatherRepository: WeatherRepository) : CurrentWeatherUseCase {

        override suspend operator fun invoke(): Flow<IResult<ICurrentWeather>> = weatherRepository.getCurrentWeather().also {
            println("CurrentWeatherUseCase called")
        }

    }
}