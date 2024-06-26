package com.nazar.petproject.data.weather

import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.entities.ILocation
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather


interface WeatherDataSource {

    suspend fun getCurrentWeather(
        temperatureUnit: MeasurementUnit,
        windSpeedUnit: MeasurementUnit,
        location: ILocation,
    ): IResult<ICurrentWeather, WeatherRepository.Exceptions>

    suspend fun getDailyWeather(
        temperatureUnit: MeasurementUnit,
        windSpeedUnit: MeasurementUnit,
        location: ILocation,
    ): IResult<IDailyWeather, WeatherRepository.Exceptions>
}