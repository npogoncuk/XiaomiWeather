package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazar.petproject.domain.suspendOnError
import com.nazar.petproject.domain.suspendOnSuccess
import com.nazar.petproject.domain.weather.use_cases.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.use_cases.DailyWeatherUseCase
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompositeWeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val dailyWeatherUseCase: DailyWeatherUseCase,
) : ViewModel() {

    private val oneTimeEventChannel = Channel<OneTimeUIEvent>()
    val oneTimeUIEvent = oneTimeEventChannel.receiveAsFlow()

    private val _weatherState = MutableStateFlow(CompositeWeatherState())
    val weatherState: StateFlow<CompositeWeatherState> = _weatherState.asStateFlow()

    init {
        getCurrentWeather()
        getDailyWeather()
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            currentWeatherUseCase().collect { result ->
                result.suspendOnError {
                    oneTimeEventChannel.send(OneTimeUIEvent.ShowToast( message ?: "Error"))
                }.suspendOnSuccess {
                    _weatherState.value = _weatherState.value.copy(currentWeather = this.data)
                }
                Log.d("CompositeWeatherViewModel", "new CurrentWeather: $result")
            }
        }
    }

    private fun getDailyWeather() {
        viewModelScope.launch {
            dailyWeatherUseCase().collect { result ->
                result.suspendOnError {
                    oneTimeEventChannel.send(OneTimeUIEvent.ShowToast(message ?: "Error"))
                }.suspendOnSuccess {
                    _weatherState.value = _weatherState.value.copy(dailyWeather = this.data)
                }
                Log.d("CompositeWeatherViewModel", "new CurrentWeather: $result")
            }
        }
    }
}

data class CompositeWeatherState(
    val currentWeather: ICurrentWeather? = null,
    val dailyWeather: IDailyWeather? = null
)