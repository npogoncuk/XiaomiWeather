package com.nazar.petproject.xiaomiweather.ui

sealed interface OneTimeUIEvent {
    data class ShowToast(val message: String) : OneTimeUIEvent
}