package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.UnitFor

interface GetTemperatureUnitsUseCase {

    operator fun invoke(): List<UnitFor.Temperature>

    class Base : GetTemperatureUnitsUseCase {
        override fun invoke(): List<UnitFor.Temperature> {
            return UnitFor.Temperature.entries
        }
    }
}