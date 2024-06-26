package com.nazar.petproject.domain.weather.use_cases

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface CurrentWeatherUseCase {

    operator fun invoke(): Flow<IResult<ICurrentWeather, WeatherUseCasesError>>

    class Base (
        private val weatherRepository: WeatherRepository,
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
        private val locationRepository: LocationRepository,
    ) : CurrentWeatherUseCase {

        override operator fun invoke(): Flow<IResult<ICurrentWeather, WeatherUseCasesError>> {
            val temperatureUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForTemperature()
            val windSpeedUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForWindSpeed()
            val currentLocationFlow = locationRepository.getCurrentLocation()

            return combineAndMapToWeatherResultFlow(
                temperatureUnitFlow,
                windSpeedUnitFlow,
                currentLocationFlow
            ) { temperatureUnit, windSpeedUnit, currentLocation ->
                weatherRepository.getCurrentWeather(temperatureUnit, windSpeedUnit, currentLocation)
            }
        }
    }
}