package com.nazar.petproject.domain.weather.model

interface IAllWeather {
    val latitude: Double
    val longitude: Double
    val generationTimeMs: Double
}