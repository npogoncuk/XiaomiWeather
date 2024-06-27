package com.nazar.petproject.data.settings.units_list_storage

import com.nazar.petproject.domain.settings.entities.units.MeasureUnitList
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import kotlinx.serialization.Serializable

object TemperatureUnits : MeasureUnitList {

    data object Celsius : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.Temperature.Celsius
    }


    data object Fahrenheit : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.Temperature.Fahrenheit
    }

    override val units: List<MeasurementUnit>
        get() = listOf(Celsius, Fahrenheit)
}