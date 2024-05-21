package com.nazar.petproject.data.weather.model.current_weather


import com.nazar.petproject.domain.weather.model.GeographicalTimeInfoWeather
import com.nazar.petproject.domain.weather.model.ICurrentWeather
import com.nazar.petproject.domain.weather.model.MeasuredPropertyName
import com.nazar.petproject.domain.weather.model.PropertyToUnitMapping
import com.nazar.petproject.domain.weather.model.UnitName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

@Serializable
internal data class CurrentWeather(
    @SerialName("latitude")
    override val latitude: Double,
    @SerialName("longitude")
    override val longitude: Double,
    @SerialName("generationtime_ms")
    val generationtimeMs: Double,
    @SerialName("utc_offset_seconds")
    override val utcOffsetSeconds: Int,
    @SerialName("timezone")
    override val timezone: String,
    @SerialName("timezone_abbreviation")
    override val timezoneAbbreviation: String,
    @SerialName("elevation")
    override val elevation: Double,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    @SerialName("current")
    override val current: Current
) : GeographicalTimeInfoWeather, ICurrentWeather, PropertyToUnitMapping {

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

    override fun getUnitOfProperty(property: KProperty<*>): String? {
        return propertyToUnitMap[property.name]
    }
}