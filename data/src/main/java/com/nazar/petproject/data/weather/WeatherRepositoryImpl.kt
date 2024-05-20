package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getAllWeather() = dataSource.getAllWeather()
}