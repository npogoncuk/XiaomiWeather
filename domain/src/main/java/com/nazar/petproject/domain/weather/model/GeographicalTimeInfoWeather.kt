package com.nazar.petproject.domain.weather.model

interface GeographicalTimeInfoWeather {
    val latitude: Double
    val longitude: Double
    val utcOffsetSeconds: Int
    val timezone: String
    val timezoneAbbreviation: String
    val elevation: Double
}