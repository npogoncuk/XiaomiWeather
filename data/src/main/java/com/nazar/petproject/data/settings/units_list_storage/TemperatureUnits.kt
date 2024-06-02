package com.nazar.petproject.data.settings.units_list_storage

import com.nazar.petproject.domain.settings.entities.units.MeasureUnitList
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor

object TemperatureUnits : MeasureUnitList {

    object Celsius : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.Temperature.Celsius
    }

    object Fahrenheit : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.Temperature.Fahrenheit
    }

    override val units: List<MeasurementUnit>
        get() = listOf(Celsius, Fahrenheit)
}