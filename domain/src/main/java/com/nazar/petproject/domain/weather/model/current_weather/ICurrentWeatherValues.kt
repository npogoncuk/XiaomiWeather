package com.nazar.petproject.domain.weather.model.current_weather

import com.nazar.petproject.domain.weather.model.general.WeatherValues

interface ICurrentWeatherValues : WeatherValues {
    //val time: String
    //val interval: Int
    val temperature2m: Double
    val relativeHumidity2m: Int
    val apparentTemperature: Double
    val isDay: Boolean
    val precipitation: Double
    val rain: Double
    val showers: Double
    val snowfall: Double
    val weatherCode: Int
    val cloudCover: Int
    val pressureMsl: Double
    val surfacePressure: Double
    val windSpeed10m: Double
    val windDirection10m: Int
    val windGusts10m: Double
}
