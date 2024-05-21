package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.model.ICurrentWeather
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeather(): ICurrentWeather {
        val l = dataSource.getCurrentWeather() as ICurrentWeather
        return l
    }
}