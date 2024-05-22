package com.nazar.petproject.domain.weather.model.general

import com.nazar.petproject.domain.weather.model.PropertyToUnitMapping
import com.nazar.petproject.domain.weather.model.general.GeographicalTimeInfoWeather

interface IWeather : PropertyToUnitMapping {
   val geographicalTimeInfoWeather: GeographicalTimeInfoWeather
   val units: MeasureUnits
   val values: WeatherValues
}