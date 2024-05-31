package com.nazar.petproject.domain.settings.entities.units

interface MeasurementUnit {
    val displayName: String
    val unitFor: UnitFor
}