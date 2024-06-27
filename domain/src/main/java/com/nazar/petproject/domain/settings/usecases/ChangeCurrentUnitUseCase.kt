package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository

interface ChangeCurrentUnitUseCase {

    suspend operator fun invoke(unit: MeasurementUnit)

    class Base(
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository
    ) : ChangeCurrentUnitUseCase {

        override suspend fun invoke(unit: MeasurementUnit) {
           when (unit.unitFor) {
                is UnitFor.Temperature -> currentUnitsSettingsRepository.saveCurrentUnitOfTemperature(unit)
                is UnitFor.WindSpeed -> currentUnitsSettingsRepository.saveCurrentUnitOfWindSpeed(unit)
            }
        }
    }
}