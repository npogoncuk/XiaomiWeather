package com.nazar.petproject.domain.weather.entities.current_weather

import com.nazar.petproject.domain.weather.entities.general.IWeather

interface ICurrentWeather : IWeather {
    override val units: ICurrentWeatherUnits
    override val values: ICurrentWeatherValues
}