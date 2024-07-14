package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.CurrentUnitsSettingsRepository
import kotlinx.coroutines.flow.Flow

interface CurrentTemperatureUnitUseCase {

    operator fun invoke(): Flow<UnitFor.Temperature>

    class Base(
        private val currentUnitsSettingsRepository: CurrentUnitsSettingsRepository
    ) : CurrentTemperatureUnitUseCase {
        override fun invoke(): Flow<UnitFor.Temperature> {
            return currentUnitsSettingsRepository.getCurrentUnitForTemperature()
        }
    }
}