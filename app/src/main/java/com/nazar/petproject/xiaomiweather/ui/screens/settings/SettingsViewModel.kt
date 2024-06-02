package com.nazar.petproject.xiaomiweather.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getTemperatureUnitsUseCase: GetTemperatureUnitsUseCase
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsScreenState())
    val settingsState = _settingsState.asStateFlow()

    init {
        _settingsState.value = _settingsState.value.copy(
            temperatureUnits = getTemperatureUnitsUseCase()
        )
    }
}

data class SettingsScreenState(
    val temperatureUnits: List<MeasurementUnit> = emptyList(),
    val currentTemperatureUnit: MeasurementUnit = object : MeasurementUnit { override val unitFor = UnitFor.Temperature.Celsius},
)