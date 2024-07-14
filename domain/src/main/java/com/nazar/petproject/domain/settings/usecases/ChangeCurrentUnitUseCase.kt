package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository

interface ChangeCurrentUnitUseCase {

    suspend operator fun invoke(unit: UnitFor)

    class Base(
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository
    ) : ChangeCurrentUnitUseCase {

        override suspend fun invoke(unit: UnitFor) {
           when (unit) {
                is UnitFor.Temperature -> currentUnitsSettingsRepository.saveCurrentUnitOfTemperature(unit)
                is UnitFor.WindSpeed -> currentUnitsSettingsRepository.saveCurrentUnitOfWindSpeed(unit)
            }
        }
    }
}