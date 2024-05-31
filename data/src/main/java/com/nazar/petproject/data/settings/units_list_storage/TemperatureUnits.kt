package com.nazar.petproject.data.settings.units_list_storage

import com.nazar.petproject.domain.settings.entities.units.MeasureUnitList
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor

object TemperatureUnits : MeasureUnitList {

    object Celsius : MeasurementUnit {
        override val displayName: String = "°C"
        override val unitFor: UnitFor = UnitFor.Temperature
    }

    object Fahrenheit : MeasurementUnit {
        override val displayName: String = "°F"
        override val unitFor: UnitFor = UnitFor.Temperature
    }

    override val units: List<MeasurementUnit>
        get() = listOf(Celsius, Fahrenheit)

    override val default: MeasurementUnit
        get() = Celsius
}