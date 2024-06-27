package com.nazar.petproject.domain.weather.entities.daily_weather

import com.nazar.petproject.domain.weather.entities.general.IWeather

interface IDailyWeather : IWeather {
    override val units: IDailyWeatherUnits
    override val values: IDailyWeatherValues
    val numberOfDays: Int
        get() = values.oneDayWeatherList.size
}