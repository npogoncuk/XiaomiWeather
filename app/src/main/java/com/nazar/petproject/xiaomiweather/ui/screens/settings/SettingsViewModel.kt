package com.nazar.petproject.xiaomiweather.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazar.petproject.domain.settings.entities.units.MeasurementUnit
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.usecases.CurrentTemperatureUnitUseCase
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
import com.nazar.petproject.domain.suspendOnError
import com.nazar.petproject.domain.suspendOnSuccess
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getTemperatureUnitsUseCase: GetTemperatureUnitsUseCase,
    private val currentTemperatureUnitUseCase: CurrentTemperatureUnitUseCase,
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsScreenState())
    val settingsState = _settingsState.asStateFlow()

    init {
        loadState()
    }

    private fun loadState() {
        _settingsState.value = _settingsState.value.copy(
            temperatureUnits = getTemperatureUnitsUseCase()
        )
        viewModelScope.launch {
            currentTemperatureUnitUseCase().collect {
                _settingsState.value = _settingsState.value.copy(
                    currentTemperatureUnit = it
                )
                Log.d("SettingsViewModel", "loadState: $it")
            }
        }
    }
}

sealed interface SettingsIntent {
    data class ChangeTemperatureUnit(val unit: MeasurementUnit) : SettingsIntent
    data class ChangeWindSpeedUnit(val unit: MeasurementUnit) : SettingsIntent


}
data class SettingsScreenState(
    val temperatureUnits: List<MeasurementUnit> = emptyList(),
    val currentTemperatureUnit: MeasurementUnit = object : MeasurementUnit { override val unitFor = UnitFor.Temperature.Celsius},
)