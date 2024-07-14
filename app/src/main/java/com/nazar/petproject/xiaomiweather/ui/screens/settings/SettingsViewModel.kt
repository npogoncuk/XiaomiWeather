package com.nazar.petproject.xiaomiweather.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.settings.usecases.ChangeCurrentUnitUseCase
import com.nazar.petproject.domain.settings.usecases.CurrentTemperatureUnitUseCase
import com.nazar.petproject.domain.settings.usecases.CurrentWindSpeedUnitUseCase
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
import com.nazar.petproject.domain.settings.usecases.GetWindSpeedUnitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getTemperatureUnitsUseCase: GetTemperatureUnitsUseCase,
    private val currentTemperatureUnitUseCase: CurrentTemperatureUnitUseCase,
    private val getWindSpeedUnitsUseCase: GetWindSpeedUnitsUseCase,
    private val currentWindSpeedUnitUseCase: CurrentWindSpeedUnitUseCase,
    private val changeCurrentUnitUseCase: ChangeCurrentUnitUseCase,
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
        _settingsState.value = _settingsState.value.copy(
            windSpeedUnits = getWindSpeedUnitsUseCase()
        )

        viewModelScope.launch {
            currentTemperatureUnitUseCase().collect {
                _settingsState.value = _settingsState.value.copy(
                    currentTemperatureUnit = it
                )
                Log.d("SettingsViewModel", "loadState currentTemperatureUnitUseCase: ${it}")
            }
        }

        viewModelScope.launch {
            currentWindSpeedUnitUseCase().collect {
                _settingsState.value = _settingsState.value.copy(
                    currentWindSpeedUnit = it
                )
                Log.d("SettingsViewModel", "loadState currentWindSpeedUnitUseCase: ${it}")
            }
        }
    }

    fun processIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ChangeUnit -> {
                changeUnit(intent.newUnit)
                Log.d("SettingsViewModel", "processIntent: ${intent}")
            }
        }
    }

    private fun changeUnit(newUnit: UnitFor) {
        viewModelScope.launch(NonCancellable) {
            changeCurrentUnitUseCase(newUnit)
        }
    }
}

sealed interface SettingsIntent {
    data class ChangeUnit(val newUnit: UnitFor) : SettingsIntent


}
data class SettingsScreenState(
    val temperatureUnits: List<UnitFor> = emptyList(),
    val currentTemperatureUnit: UnitFor = UnitFor.Temperature.CELSIUS,
    val windSpeedUnits: List<UnitFor> = emptyList(),
    val currentWindSpeedUnit: UnitFor = UnitFor.WindSpeed.METERS_PER_SECOND,
)