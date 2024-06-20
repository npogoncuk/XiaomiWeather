package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.entities.ILocation
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val test_delay = 1L
class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        temperatureUnit: MeasurementUnit,
        windSpeedUnit: MeasurementUnit,
        location: ILocation,
    ): Flow<IResult<ICurrentWeather, WeatherRepository.Exceptions>> = flow {
        delay(test_delay)
        val currentWeatherResult = dataSource.getCurrentWeather(temperatureUnit, windSpeedUnit)
        emit(currentWeatherResult)
    }.flowOn(dispatcher)

    override suspend fun getDailyWeather(
        temperatureUnit: MeasurementUnit,
        windSpeedUnit: MeasurementUnit,
    ): Flow<IResult<IDailyWeather, WeatherRepository.Exceptions>> = flow {
        delay(test_delay)
        val dailyWeather = dataSource.getDailyWeather(temperatureUnit, windSpeedUnit)
        emit(dailyWeather)
    }.flowOn(dispatcher)
}