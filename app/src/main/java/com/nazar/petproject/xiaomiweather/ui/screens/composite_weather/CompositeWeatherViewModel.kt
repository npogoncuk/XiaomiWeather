package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.suspendOnError
import com.nazar.petproject.domain.suspendOnSuccess
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeather
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import com.nazar.petproject.domain.weather.use_cases.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.use_cases.DailyWeatherUseCase
import com.nazar.petproject.domain.weather.use_cases.WeatherUseCasesError
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
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

    private var collectingJob: Job? = null

    private val currentWeatherCollector: FlowCollector<IResult<ICurrentWeather, WeatherUseCasesError>> =
        FlowCollector { result ->
            result.suspendOnError {
                defaultOnError(this)
            }.suspendOnSuccess {
                _weatherState.value = _weatherState.value.copy(currentWeather = this.data)
            }
            Log.d("CompositeWeatherViewModel", "new CurrentWeather: $result")
        }

    private val dailyWeatherCollector: FlowCollector<IResult<IDailyWeather, WeatherUseCasesError>> =
        FlowCollector { result ->
            result.suspendOnError {
                defaultOnError(this)
            }.suspendOnSuccess {
                _weatherState.value = _weatherState.value.copy(dailyWeather = this.data)
            }
            Log.d("CompositeWeatherViewModel", "new DailyWeather: $result")
        }

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

    fun refreshData() {
        collectingJob?.cancel()

        _weatherState.value = _weatherState.value.copy(shouldRequestLocationPermission = false)
        launchCollectingCurrentAndDailyWeather()
    }


    private fun launchCollectingCurrentAndDailyWeather() {
        collectingJob = viewModelScope.launch {
            launch {
                currentWeatherUseCase().collect(currentWeatherCollector)
            }
            launch {
                dailyWeatherUseCase().collect(dailyWeatherCollector)
            }
        }
    }

    fun processIntent(intent: CompositeWeatherIntent) {
        when (intent) {
            is CompositeWeatherIntent.OnRefreshData -> refreshData()
        }
    }
}

data class CompositeWeatherState(
    val currentWeather: ICurrentWeather? = null,
    val dailyWeather: IDailyWeather? = null,
    val shouldRequestLocationPermission: Boolean = false,
)

sealed interface CompositeWeatherIntent {
    data object OnRefreshData : CompositeWeatherIntent
}