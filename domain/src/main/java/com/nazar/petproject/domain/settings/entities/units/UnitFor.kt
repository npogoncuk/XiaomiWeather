package com.nazar.petproject.domain.settings.entities.units

sealed interface UnitFor {

    enum class Temperature : UnitFor {
        CELSIUS, FAHRENHEIT
    }

    enum class WindSpeed : UnitFor {
        METERS_PER_SECOND,
        KILOMETERS_PER_HOUR,
        MILES_PER_HOUR,
        KNOTS,
    }
}