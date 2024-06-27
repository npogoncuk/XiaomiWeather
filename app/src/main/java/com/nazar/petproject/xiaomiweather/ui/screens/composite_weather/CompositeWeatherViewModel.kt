package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.suspendOnError
import com.nazar.petproject.domain.suspendOnSuccess
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import com.nazar.petproject.domain.weather.use_cases.GetAllWeatherDataUseCase
import com.nazar.petproject.domain.weather.use_cases.WeatherUseCasesError
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompositeWeatherViewModel @Inject constructor(
    private val allWeatherDataUseCase: GetAllWeatherDataUseCase,
) : ViewModel() {

    private val oneTimeEventChannel = Channel<OneTimeUIEvent>()
    val oneTimeUIEvent = oneTimeEventChannel.receiveAsFlow()

    private val _weatherState = MutableStateFlow(CompositeWeatherState())
    val weatherState: StateFlow<CompositeWeatherState> = _weatherState.asStateFlow()

    private var collectingJob: Job? = null

    private suspend fun defaultOnError(errorResult: IResult.Error<WeatherUseCasesError>) {
        oneTimeEventChannel.send(OneTimeUIEvent.ShowToast(errorResult.exception.message ?: "Error"))
        if (errorResult.exception is WeatherUseCasesError.LocationRepositoryError) {
            Log.d("CompositeWeatherViewModel", "LocationRepositoryError")
            _weatherState.value = _weatherState.value.copy(shouldRequestLocationPermission = true)
        }
    }

    init {
        launchCollectingCurrentAndDailyWeather()
    }


    private fun launchCollectingCurrentAndDailyWeather() {


        collectingJob = viewModelScope.launch {
            allWeatherDataUseCase().collect { result ->

                _weatherState.value = _weatherState.value.copy(isRefreshing = result is IResult.Loading)

                result.suspendOnError {
                    defaultOnError(this)
                }.suspendOnSuccess {
                    _weatherState.value = _weatherState.value.copy(
                        currentWeather = this.data.current,
                        dailyWeather = this.data.daily,
                    )
                }
            }
        }
    }



    fun processIntent(intent: CompositeWeatherIntent) {
        when (intent) {
            is CompositeWeatherIntent.OnRefreshData -> refreshData()
        }
    }

    private fun refreshData() {
        collectingJob?.cancel()

        // set to empty state
        _weatherState.value = CompositeWeatherState()

        launchCollectingCurrentAndDailyWeather()
    }
}

data class CompositeWeatherState(
    val currentWeather: ICurrentWeather? = null,
    val dailyWeather: IDailyWeather? = null,
    val shouldRequestLocationPermission: Boolean = false,
    val isRefreshing: Boolean = false,
)

sealed interface CompositeWeatherIntent {
    data object OnRefreshData : CompositeWeatherIntent
}