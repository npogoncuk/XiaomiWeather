package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun getCurrentWeather(): Flow<IResult<ICurrentWeather>> = flow {
        emit(IResult.Loading)
        val we = dataSource.getCurrentWeather()
        emit(IResult.Success(we))
        emit(IResult.Error())
    }.flowOn(dispatcher)
}