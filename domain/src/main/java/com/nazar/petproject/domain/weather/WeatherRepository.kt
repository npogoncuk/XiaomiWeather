package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {

    suspend fun getCurrentWeather(): Flow<IResult<ICurrentWeather>>
}