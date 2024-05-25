package com.nazar.petproject.data.weather.model.daily_weather


import com.nazar.petproject.domain.weather.entities.MeasuredPropertyName
import com.nazar.petproject.domain.weather.entities.PropertyToUnitMapping
import com.nazar.petproject.domain.weather.entities.UnitName
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeatherUnits
import com.nazar.petproject.domain.weather.entities.general.GeographicalTimeInfoWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DailyWeather(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("generationtime_ms")
    val generationtimeMs: Double,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerialName("elevation")
    val elevation: Double,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits,
    @SerialName("daily")
    val daily: Daily
) : IDailyWeather, PropertyToUnitMapping {

    private val _propertyToUnitMap = createPropertyToUnitMap(dailyUnits)

    override val propertyToUnitMap: Map<MeasuredPropertyName, UnitName>
        get() = _propertyToUnitMap

    override val geographicalTimeInfoWeather = GeographicalTimeInfoWeather.createInstance(
        latitude = latitude,
        longitude = longitude,
        utcOffsetSeconds = utcOffsetSeconds,
        timezone = timezone,
        timezoneAbbreviation = timezoneAbbreviation,
        elevation = elevation
    )

    override val units: IDailyWeatherUnits = dailyUnits

    override val values = daily
}