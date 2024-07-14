package com.nazar.petproject.domain.weather.use_cases

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface GetAllWeatherDataUseCase {
    operator fun invoke(): Flow<IResult<AllWeatherData, WeatherUseCasesError>>

    class Base(
        private val weatherRepository: WeatherRepository,
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository,
        private val locationRepository: LocationRepository,
    ) : GetAllWeatherDataUseCase {

        override operator fun invoke(): Flow<IResult<AllWeatherData, WeatherUseCasesError>> {
            val temperatureUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForTemperature()
            val windSpeedUnitFlow = currentUnitsSettingsRepository.getCurrentUnitForWindSpeed()
            val currentLocationFlow = locationRepository.getCurrentLocation()

            val currentWeatherFlow = combineAndMapToWeatherResultFlow(
                temperatureUnitFlow,
                windSpeedUnitFlow,
                currentLocationFlow
            ) { temperatureUnit: UnitFor.Temperature, windSpeedUnit: UnitFor.WindSpeed, currentLocation ->
                weatherRepository.getCurrentWeather(
                    temperatureUnit,
                    windSpeedUnit,
                    currentLocation
                )
            }

            val dailyWeatherFlow = combineAndMapToWeatherResultFlow(
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

            val allWeatherFlow = currentWeatherFlow.combine(dailyWeatherFlow) { current, daily ->
                when {
                    current is IResult.Loading -> current
                    daily is IResult.Loading -> daily

                    current is IResult.Error -> current
                    daily is IResult.Error -> daily
                    // then we can be sure that both are success
                    else -> {
                        val currentSuccess = current as IResult.Success
                        val dailySuccess = daily as IResult.Success

                        IResult.Success(AllWeatherData(currentSuccess.data, dailySuccess.data))
                    }
                }
            }
            return allWeatherFlow
        }
    }
}

data class AllWeatherData(
    val current: ICurrentWeather,
    val daily: IDailyWeather,
)