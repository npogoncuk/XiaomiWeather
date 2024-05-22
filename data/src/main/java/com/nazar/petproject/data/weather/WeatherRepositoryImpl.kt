package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.model.current_weather.ICurrentWeather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeather(): ICurrentWeather {
        val l = dataSource.getCurrentWeather() as ICurrentWeather
        return l
    }
}