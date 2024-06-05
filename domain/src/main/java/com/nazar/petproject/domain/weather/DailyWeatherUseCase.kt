package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge

interface DailyWeatherUseCase {

    suspend operator fun invoke(): Flow<IResult<IDailyWeather>>

    class Base(
        private val weatherRepository: WeatherRepository,
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
    ) : DailyWeatherUseCase {

        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend operator fun invoke(): Flow<IResult<IDailyWeather>> {
            val temperatureUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForTemperature()
            val windSpeedUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForWindSpeed()

            val currentWeatherResultFlow = combine(temperatureUnitFlow, windSpeedUnitFlow) { temperatureUnit, windSpeedUnit ->
                weatherRepository.getDailyWeather(temperatureUnit, windSpeedUnit)
            }.flattenMerge()

            return currentWeatherResultFlow
        }

    }
}