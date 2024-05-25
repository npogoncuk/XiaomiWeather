package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather


interface WeatherDataSource {

    suspend fun getCurrentWeather(): IResult<ICurrentWeather>

    suspend fun getDailyWeather(): IResult<IDailyWeather>
}