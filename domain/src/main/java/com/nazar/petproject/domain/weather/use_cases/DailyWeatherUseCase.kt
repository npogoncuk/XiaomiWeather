package com.nazar.petproject.domain.weather.use_cases

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.flow.Flow

interface DailyWeatherUseCase {

    operator fun invoke(): Flow<IResult<IDailyWeather, WeatherUseCasesError>>

    class Base(
        private val weatherRepository: WeatherRepository,
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
        private val locationRepository: LocationRepository,
    ) : DailyWeatherUseCase {

        override operator fun invoke(): Flow<IResult<IDailyWeather, WeatherUseCasesError>> {
            val temperatureUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForTemperature()
            val windSpeedUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForWindSpeed()
            val currentLocationFlow = locationRepository.getCurrentLocation()

            return combineAndMapToWeatherResultFlow(
                temperatureUnitFlow,
                windSpeedUnitFlow,
                currentLocationFlow
            ) { temperatureUnit: UnitFor.Temperature, windSpeedUnit: UnitFor.WindSpeed, currentLocation ->
                weatherRepository.getDailyWeather(
                    temperatureUnit,
                    windSpeedUnit,
                    currentLocation
                )
            }
        }
    }
}