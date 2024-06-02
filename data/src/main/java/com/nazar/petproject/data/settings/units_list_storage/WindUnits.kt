package com.nazar.petproject.data.settings.units_list_storage

import com.nazar.petproject.domain.settings.entities.units.MeasureUnitList
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor

object WindUnits : MeasureUnitList {

    data object MetersPerSecond : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.WindSpeed.MetersPerSecond
    }

    data object KilometersPerHour : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.WindSpeed.KilometersPerHour
    }

    data object MilesPerHour : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.WindSpeed.MilesPerHour
    }

    data object Knots : MeasurementUnit {
        override val unitFor: UnitFor = UnitFor.WindSpeed.Knots
    }

    override val units: List<MeasurementUnit>
        get() = listOf(MetersPerSecond, KilometersPerHour, MilesPerHour, Knots)

}