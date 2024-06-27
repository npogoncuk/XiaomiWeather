package com.nazar.petproject.domain.settings.entities.units

sealed interface UnitFor {
    interface Temperature : UnitFor {
        data object Celsius : Temperature

        data object Fahrenheit : Temperature

    }
    interface WindSpeed : UnitFor {
        data object MetersPerSecond : WindSpeed
        data object KilometersPerHour : WindSpeed
        data object MilesPerHour : WindSpeed
        data object Knots : WindSpeed
    }
}