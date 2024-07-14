package com.nazar.petproject.domain.settings.usecases

import com.nazar.petproject.domain.settings.entities.units.UnitFor

interface GetWindSpeedUnitsUseCase {

    operator fun invoke(): List<UnitFor.WindSpeed>

    class Base : GetWindSpeedUnitsUseCase {

        override fun invoke(): List<UnitFor.WindSpeed> {
            return UnitFor.WindSpeed.entries
        }
    }
}