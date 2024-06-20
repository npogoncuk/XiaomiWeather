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

        @OptIn(ExperimentalCoroutinesApi::class)
        override operator fun invoke(): Flow<IResult<ICurrentWeather, WeatherUseCasesError>> {
            val temperatureUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForTemperature()
            val windSpeedUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForWindSpeed()
            val currentLocationFlow = locationRepository.getCurrentLocation()

            val a1 = combine(temperatureUnitFlow, windSpeedUnitFlow, currentLocationFlow) { temperatureUnit, windSpeedUnit, currentLocation ->
                Triple(temperatureUnit, windSpeedUnit, currentLocation).also {
                    println("combine $it")
                }
            }.map { (temperatureUnit, windSpeedUnit, currentLocation) ->
                when(currentLocation) {
                    is IResult.Success -> weatherRepository.getCurrentWeather(temperatureUnit, windSpeedUnit, currentLocation.data).also {
                        println("weatherRepository.getCurrentWeather ${currentLocation.data}")
                    }
                    is IResult.Error -> flowOf(currentLocation)
                }
            }
            val a2 = a1.flattenConcat()
            val a3 = a2.map { result ->
                when(result) {
                    is IResult.Success -> result
                    is IResult.Error -> {
                        when(result.exception) {
                            is WeatherRepository.Exceptions -> IResult.Error(WeatherUseCasesError.WeatherRepositoryError(result.exception))
                            is LocationRepository.Exceptions -> IResult.Error(WeatherUseCasesError.LocationRepositoryError(result.exception))
                            else -> TODO()
                        }
                    }
                }
            }
            return a3
        }
    }
}