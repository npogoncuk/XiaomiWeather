package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.AvailableUnitsSettingsRepository

interface GetWindSpeedUnitsUseCase {

    operator fun invoke(): List<MeasurementUnit>

    class Base(
        private val availableUnitsSettingsRepository: AvailableUnitsSettingsRepository
    ) : GetWindSpeedUnitsUseCase {

        override fun invoke(): List<MeasurementUnit> {
            return availableUnitsSettingsRepository.getAvailableUnits(object : UnitFor.WindSpeed {})
        }
    }
}