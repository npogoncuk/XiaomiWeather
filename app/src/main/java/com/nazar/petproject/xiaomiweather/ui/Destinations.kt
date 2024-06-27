package com.nazar.petproject.xiaomiweather.ui

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object CompositeWeather : Destination

    @Serializable
    data object Settings : Destination
}