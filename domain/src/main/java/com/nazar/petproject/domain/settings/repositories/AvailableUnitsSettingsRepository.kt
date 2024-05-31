package com.nazar.petproject.domain.settings.repositories

import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor

interface AvailableUnitsSettingsRepository {
    fun getAvailableUnits(unitFor: UnitFor): List<MeasurementUnit>
}