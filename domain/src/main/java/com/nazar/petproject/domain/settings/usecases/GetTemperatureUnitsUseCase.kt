package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.repositories.AvailableUnitsSettingsRepository

interface GetTemperatureUnitsUseCase {

    operator fun invoke(): List<MeasurementUnit>

    class Base(
        private val availableUnitsSettingsRepository: AvailableUnitsSettingsRepository
    ) : GetTemperatureUnitsUseCase {

        override fun invoke(): List<MeasurementUnit> {
            return availableUnitsSettingsRepository.getAvailableUnits(object : UnitFor.Temperature {})
        }
    }
}