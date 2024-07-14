package com.nazar.petproject.domain.weather.use_cases

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.LocationRepository
import com.nazar.petproject.domain.location.entities.ILocation
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.weather.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> combineAndMapToWeatherResultFlow(
    temperatureUnitFlow: Flow<UnitFor.Temperature>,
    windSpeedUnitFlow: Flow<UnitFor.WindSpeed>,
    locationFlow: Flow<IResult<ILocation, LocationRepository.Exceptions>>,
    onGetWeather: (UnitFor.Temperature, UnitFor.WindSpeed, ILocation) -> Flow<IResult<T, WeatherRepository.Exceptions>>
) : Flow<IResult<T, WeatherUseCasesError>> {
    return combine(
        temperatureUnitFlow,
        windSpeedUnitFlow,
        locationFlow
    ) { temperatureUnit, windSpeedUnit, currentLocation ->
        Triple(temperatureUnit, windSpeedUnit, currentLocation)
    }.map { (temperatureUnit, windSpeedUnit, currentLocation) ->
        when (currentLocation) {
            is IResult.Loading -> flowOf(currentLocation)

            is IResult.Success -> onGetWeather(
                temperatureUnit,
                windSpeedUnit,
                currentLocation.data
            )

            is IResult.Error -> flowOf(currentLocation)
        }
    }.flattenConcat()
        .map { result ->
            when (result) {
                is IResult.Loading -> result
                is IResult.Success -> result
                is IResult.Error -> {
                    when (result.exception) {
                        is WeatherRepository.Exceptions -> IResult.Error(
                            WeatherUseCasesError.WeatherRepositoryError(
                                result.exception
                            )
                        )

                        is LocationRepository.Exceptions -> IResult.Error(
                            WeatherUseCasesError.LocationRepositoryError(
                                result.exception
                            )
                        )

                        else -> TODO()
                    }
                }
            }
        }
        .cancellable()
}