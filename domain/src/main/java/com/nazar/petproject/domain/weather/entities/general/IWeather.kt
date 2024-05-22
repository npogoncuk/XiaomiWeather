package com.nazar.petproject.domain.weather.entities.general

import com.nazar.petproject.domain.weather.entities.PropertyToUnitMapping

interface IWeather : PropertyToUnitMapping {
   val geographicalTimeInfoWeather: GeographicalTimeInfoWeather
   val units: MeasureUnits
   val values: WeatherValues
}