package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {

    suspend fun getCurrentWeather(): Flow<IResult<ICurrentWeather>>

    suspend fun getDailyWeather(): Flow<IResult<IDailyWeather>>
}