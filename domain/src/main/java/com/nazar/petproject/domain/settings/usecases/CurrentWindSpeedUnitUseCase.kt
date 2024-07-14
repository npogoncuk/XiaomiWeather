package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import kotlinx.coroutines.flow.Flow

interface CurrentWindSpeedUnitUseCase {
    operator fun invoke(): Flow<UnitFor.WindSpeed>

    class Base(
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository
    ) : CurrentWindSpeedUnitUseCase {
        override fun invoke(): Flow<UnitFor.WindSpeed> {
            return currentUnitsSettingsRepository.getCurrentUnitForWindSpeed()
        }
    }
}