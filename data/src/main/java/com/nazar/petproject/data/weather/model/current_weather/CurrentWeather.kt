package com.nazar.petproject.data.weather.model.current_weather


import com.nazar.petproject.domain.weather.model.general.GeographicalTimeInfoWeather
import com.nazar.petproject.domain.weather.model.MeasuredPropertyName
import com.nazar.petproject.domain.weather.model.PropertyToUnitMapping
import com.nazar.petproject.domain.weather.model.UnitName
import com.nazar.petproject.domain.weather.model.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.model.current_weather.ICurrentWeatherUnits
import com.nazar.petproject.domain.weather.model.current_weather.ICurrentWeatherValues
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentWeather(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("generationtime_ms")
    val generationTimeMs: Double,
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
    val current: Current
) : ICurrentWeather, PropertyToUnitMapping {

    private val _propertyToUnitMap = createPropertyToUnitMap(currentUnits)
    /* mutableMapOf<MeasuredPropertyName, UnitName>().apply {
    val properties = CurrentUnits::class.memberProperties
    properties.forEach { property ->
        val value = property.call(currentUnits) as? String
        if (value != null) {
            this[property.name] = value
        }
    }
}*/

    override val propertyToUnitMap: Map<MeasuredPropertyName, UnitName>
        get() = _propertyToUnitMap

    override val geographicalTimeInfoWeather: GeographicalTimeInfoWeather
        get() = GeographicalTimeInfoWeather.createInstance(
            latitude = latitude,
            longitude = longitude,
            utcOffsetSeconds = utcOffsetSeconds,
            timezone = timezone,
            timezoneAbbreviation = timezoneAbbreviation,
            elevation = elevation
        )

    override val units: ICurrentWeatherUnits
        get() = currentUnits
    override val values: ICurrentWeatherValues
        get() = current
}