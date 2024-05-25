package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.flow.Flow

interface DailyWeatherUseCase {

    suspend operator fun invoke(): Flow<IResult<IDailyWeather>>

    class Base(private val weatherRepository: WeatherRepository) : DailyWeatherUseCase {

        override suspend operator fun invoke(): Flow<IResult<IDailyWeather>> = weatherRepository.getDailyWeather().also {
            println("DailyWeatherUseCase called")
        }

    }
}