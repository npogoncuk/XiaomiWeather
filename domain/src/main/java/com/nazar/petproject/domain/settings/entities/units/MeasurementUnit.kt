package com.nazar.petproject.domain.settings.entities.units

interface MeasurementUnit {
    val unitFor: UnitFor


}

infix fun MeasurementUnit.isSame(other: MeasurementUnit): Boolean = this.unitFor == other.unitFor
