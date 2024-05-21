package com.nazar.petproject.domain.weather.model

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

typealias MeasuredPropertyName = String
typealias UnitName = String

interface PropertyToUnitMapping {
    val propertyToUnitMap: Map<MeasuredPropertyName, UnitName>

    fun getUnitOfProperty(property: KProperty<*>): String? {
        return propertyToUnitMap[property.name]
    }

    fun <T : Any> createPropertyToUnitMap(instance: T): Map<MeasuredPropertyName, UnitName> {
        return instance::class.memberProperties
            .mapNotNull { property ->
                val value = property.call(instance) as? String
                value?.let { property.name to it }
            }
            .toMap()
    }
}