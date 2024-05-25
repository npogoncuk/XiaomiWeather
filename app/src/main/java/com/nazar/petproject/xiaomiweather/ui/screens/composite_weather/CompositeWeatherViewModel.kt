package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.onError
import com.nazar.petproject.domain.onSuccess
import com.nazar.petproject.domain.suspendOnError
import com.nazar.petproject.domain.weather.CurrentWeatherUseCase
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompositeWeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase
) : ViewModel() {

    private val oneTimeEventChannel = Channel<OneTimeUIEvent>()
    val oneTimeUIEvent = oneTimeEventChannel.receiveAsFlow()

    init {
        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            currentWeatherUseCase().collect { result ->
                result.suspendOnError {
                    oneTimeEventChannel.send(OneTimeUIEvent.ShowToast("Error"))
                }
            }
        }
    }
}