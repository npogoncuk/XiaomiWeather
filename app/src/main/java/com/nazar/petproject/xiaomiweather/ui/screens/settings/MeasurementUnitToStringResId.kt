package com.nazar.petproject.xiaomiweather.ui.screens.settings

import androidx.annotation.StringRes
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.xiaomiweather.R

@get:StringRes
val MeasurementUnit.subtitle: Int
    get() = when (this.unitFor) {
        is UnitFor.Temperature.Celsius -> R.string.celsius
        is UnitFor.Temperature.Fahrenheit -> R.string.farenheit

        is UnitFor.WindSpeed.MetersPerSecond -> R.string.meters_per_second
        is UnitFor.WindSpeed.KilometersPerHour -> R.string.kilometers_per_hour
        is UnitFor.WindSpeed.MilesPerHour -> R.string.miles_per_hour
        is UnitFor.WindSpeed.Knots -> R.string.knots

        else -> throw IllegalArgumentException("Unknown unit type")
    }
