package com.nazar.petproject.domain.settings.entities.units

sealed interface UnitFor {
    data object Temperature : UnitFor
    data object WindSpeed : UnitFor
}