package com.nazar.petproject.xiaomiweather.ui.screens.settings

import androidx.annotation.StringRes
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.xiaomiweather.R

@get:StringRes
val UnitFor.subtitle: Int
    get() = when (this) {
        UnitFor.Temperature.CELSIUS -> R.string.celsius
        UnitFor.Temperature.FAHRENHEIT -> R.string.fahrenheit

        UnitFor.WindSpeed.METERS_PER_SECOND -> R.string.meters_per_second
        UnitFor.WindSpeed.KILOMETERS_PER_HOUR -> R.string.kilometers_per_hour
        UnitFor.WindSpeed.MILES_PER_HOUR -> R.string.miles_per_hour
        UnitFor.WindSpeed.KNOTS -> R.string.knots

        else -> throw IllegalArgumentException("Unknown unit type")
    }
