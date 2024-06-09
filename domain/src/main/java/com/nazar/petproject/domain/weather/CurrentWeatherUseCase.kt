package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge

interface CurrentWeatherUseCase {

    suspend operator fun invoke(): Flow<IResult<ICurrentWeather>>

    class Base (
        private val weatherRepository: WeatherRepository,
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
    ) : CurrentWeatherUseCase {

        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend operator fun invoke(): Flow<IResult<ICurrentWeather>> {
            val temperatureUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForTemperature()
            val windSpeedUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForWindSpeed()

            return temperatureUnitFlow.combine(windSpeedUnitFlow) { temperatureUnit, windSpeedUnit ->
                weatherRepository.getCurrentWeather(temperatureUnit, windSpeedUnit)
            }.flatMapLatest { it }
        }

    }
}