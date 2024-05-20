package com.nazar.petproject.data.weather.model


import com.nazar.petproject.domain.weather.model.IAllWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllWeather(
    @SerialName("latitude")
    override val latitude: Double,
    @SerialName("longitude")
    override val longitude: Double,
    @SerialName("generationtime_ms")
    override val generationTimeMs: Double,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerialName("elevation")
    val elevation: Double,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    @SerialName("current")
    val current: Current,
    @SerialName("hourly_units")
    val hourlyUnits: HourlyUnits,
    @SerialName("hourly")
    val hourly: Hourly,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits,
    @SerialName("daily")
    val daily: Daily
) : IAllWeather