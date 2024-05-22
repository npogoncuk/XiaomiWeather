package com.nazar.petproject.domain.weather.model.current_weather

import com.nazar.petproject.domain.weather.model.general.IWeather

interface ICurrentWeather : IWeather {
    override val units: ICurrentWeatherUnits
    override val values: ICurrentWeatherValues
}