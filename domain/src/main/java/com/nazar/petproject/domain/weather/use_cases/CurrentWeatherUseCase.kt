package com.nazar.petproject.domain.weather.use_cases

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import kotlinx.coroutines.flow.Flow

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
            ) { temperatureUnit: UnitFor.Temperature, windSpeedUnit: UnitFor.WindSpeed, currentLocation ->
                weatherRepository.getCurrentWeather(temperatureUnit, windSpeedUnit, currentLocation)
            }
        }
    }
}