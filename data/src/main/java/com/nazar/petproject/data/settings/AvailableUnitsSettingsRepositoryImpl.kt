package com.nazar.petproject.data.settings

import com.nazar.petproject.data.settings.units_list_storage.TemperatureUnits
import com.nazar.petproject.data.settings.units_list_storage.WindUnits
import com.nazar.petproject.domain.settings.repositories.AvailableUnitsSettingsRepository
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import javax.inject.Inject

class AvailableUnitsSettingsRepositoryImpl @Inject constructor() :
    AvailableUnitsSettingsRepository {

    override fun getAvailableUnits(unitFor: UnitFor): List<MeasurementUnit> {
        return when(unitFor) {
            is UnitFor.Temperature -> TemperatureUnits.units
            is UnitFor.WindSpeed -> WindUnits.units
        }
    }
}
