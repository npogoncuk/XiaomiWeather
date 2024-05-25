package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun getCurrentWeather(): Flow<IResult<ICurrentWeather>> = flow {
        emit(IResult.Error(message = "Current Weather Error"))
        emit(IResult.Loading)
        delay(3000)
        val we = dataSource.getCurrentWeather()
        emit(we)

        delay(5000)
        emit(IResult.Error())
    }.flowOn(dispatcher)

    override suspend fun getDailyWeather(): Flow<IResult<IDailyWeather>> = flow {
        val dailyWeather = dataSource.getDailyWeather()
        emit(dailyWeather)
        emit(IResult.Error(message = "Daily Weather Error"))
    }.flowOn(dispatcher)
}