package com.nazar.petproject.domain.weather.entities.general

interface GeographicalTimeInfoWeather {
    val latitude: Double
    val longitude: Double
    val utcOffsetSeconds: Int
    val timezone: String
    val timezoneAbbreviation: String
    val elevation: Double

    companion object {
        fun createInstance(
            latitude: Double,
            longitude: Double,
            utcOffsetSeconds: Int,
            timezone: String,
            timezoneAbbreviation: String,
            elevation: Double
        ): GeographicalTimeInfoWeather = object : GeographicalTimeInfoWeather {
            override val latitude: Double = latitude
            override val longitude: Double = longitude
            override val utcOffsetSeconds: Int = utcOffsetSeconds
            override val timezone: String = timezone
            override val timezoneAbbreviation: String = timezoneAbbreviation
            override val elevation: Double = elevation
        }
    }

}