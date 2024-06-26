package com.nazar.petproject.domain.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.entities.ILocation
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {

    fun getCurrentWeather(
        temperatureUnit: MeasurementUnit,
        windSpeedUnit: MeasurementUnit,
        location: ILocation,
    ): Flow<IResult<ICurrentWeather, Exceptions>>

    fun getDailyWeather(
        temperatureUnit: MeasurementUnit,
        windSpeedUnit: MeasurementUnit,
    ): Flow<IResult<IDailyWeather, Exceptions>>


    sealed class Exceptions(
        val message: String? = null,
    ) {
        data class NetworkException(
            val exception: Throwable? = null,
            // if no exception
            val errorBody: String? = null,
        ) : Exceptions(exception?.message ?: errorBody)

        data class ApiCallError(
            val payload: Any? = null,
        ) : Exceptions(payload.toString())

        data class UnknownException(
            val exception: Throwable,
        ) : Exceptions(exception.message)
    }
}

