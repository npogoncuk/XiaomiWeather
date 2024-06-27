package com.nazar.petproject.domain.weather.entities.daily_weather

import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.time.Duration

interface IOneDayWeather {
    val date: LocalDate
    val weatherCode: Int
    val temperatureMax: Int
    val temperatureMin: Int
    val apparentTemperatureMax: Int
    val apparentTemperatureMin: Int
    val sunriseTime: LocalDateTime
    val sunsetTime: LocalDateTime
    val daylightDuration: Duration
    val sunshineDuration: Duration
    val uvIndexMax: Int
    val precipitationSum: Int
    val rainSum: Int
    val showersSum: Int
    val snowfallSum: Int
    val precipitationHours: Double
    val precipitationProbability: Int
    val windSpeed: Double
    val windGusts: Double
    val windDirection: Int
}

fun IOneDayWeather.defaultToString(): String {
    return "IOneDayWeather(date=$date, weatherCode=$weatherCode, temperatureMax=$temperatureMax, temperatureMin=$temperatureMin, apparentTemperatureMax=$apparentTemperatureMax, apparentTemperatureMin=$apparentTemperatureMin, sunriseTime=$sunriseTime, sunsetTime=$sunsetTime, daylightDuration=$daylightDuration, sunshineDuration=$sunshineDuration, uvIndexMax=$uvIndexMax, precipitationSum=$precipitationSum, rainSum=$rainSum, showersSum=$showersSum, snowfallSum=$snowfallSum, precipitationHours=$precipitationHours, precipitationProbability=$precipitationProbability, windSpeed=$windSpeed, windGusts=$windGusts, windDirection=$windDirection)"
}
