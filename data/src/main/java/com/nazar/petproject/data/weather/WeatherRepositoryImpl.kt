package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.entities.ILocation
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val test_delay = 3L
class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource,
    private val dispatcher: CoroutineDispatcher
) : WeatherRepository {

    override fun getCurrentWeather(
        temperatureUnit: UnitFor.Temperature,
        windSpeedUnit: UnitFor.WindSpeed,
        location: ILocation,
    ): Flow<IResult<ICurrentWeather, WeatherRepository.Exceptions>> = flow {
        emit(IResult.Loading)
        delay(test_delay)
        val currentWeatherResult = dataSource.getCurrentWeather(temperatureUnit, windSpeedUnit, location)
        emit(currentWeatherResult)
    }.flowOn(dispatcher)

    override fun getDailyWeather(
        temperatureUnit: UnitFor.Temperature,
        windSpeedUnit: UnitFor.WindSpeed,
        location: ILocation,
    ): Flow<IResult<IDailyWeather, WeatherRepository.Exceptions>> = flow {
        emit(IResult.Loading)
        delay(test_delay)
        val dailyWeather = dataSource.getDailyWeather(temperatureUnit, windSpeedUnit, location)
        emit(dailyWeather)
    }.flowOn(dispatcher)
}