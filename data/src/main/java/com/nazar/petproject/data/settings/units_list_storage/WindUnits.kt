package com.nazar.petproject.data.settings.units_list_storage

import com.nazar.petproject.domain.settings.entities.units.MeasureUnitList
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor

object WindUnits : MeasureUnitList {

    object MetersPerSecond : MeasurementUnit {
        override val displayName: String = "m/s"
        override val unitFor: UnitFor = UnitFor.WindSpeed
    }

    object KilometersPerHour : MeasurementUnit {
        override val displayName: String = "km/h"
        override val unitFor: UnitFor = UnitFor.WindSpeed
    }

    object MilesPerHour : MeasurementUnit {
        override val displayName: String = "mph"
        override val unitFor: UnitFor = UnitFor.WindSpeed
    }

    object Knots : MeasurementUnit {
        override val displayName: String = "kn"
        override val unitFor: UnitFor = UnitFor.WindSpeed
    }

    override val units: List<MeasurementUnit>
        get() = listOf(MetersPerSecond, KilometersPerHour, MilesPerHour, Knots)

    override val default: MeasurementUnit
        get() = MetersPerSecond
}