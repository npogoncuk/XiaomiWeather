package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nazar.petproject.xiaomiweather.ui.Dimensions.DEFAULT_SMALL_PADDING
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.CompositeWeatherTopAppBar
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.CurrentTemperatureBlock
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.FiveDayForecastBlock
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.WeatherDetailsBlock


@Composable
fun CompositeWeatherScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<CompositeWeatherViewModel>()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.oneTimeUIEvent.collect { uiEvent ->
            val message = (uiEvent as? OneTimeUIEvent.ShowToast)?.message.toString()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val state by viewModel.weatherState.collectAsState()
    Scaffold(
        topBar = { CompositeWeatherTopAppBar() }
    ) { paddingValues ->

        val scrollState = rememberLazyListState()

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            item { CurrentTemperatureBlock(
                modifier = Modifier.padding(DEFAULT_SMALL_PADDING),
                currentTemperature = state.currentWeather?.values?.temperature ?: -25,
                temperatureUnit = "°C",
                highLow = "High 25°C / Low 25°C",
                aqi = "AQI 25")
            }
            item {
                val dailyForecast = state.dailyWeather
                if (dailyForecast != null) {
                     FiveDayForecastBlock(
                         dailyWeatherList = dailyForecast.values.oneDayWeatherList,
                         modifier = Modifier.padding(DEFAULT_SMALL_PADDING)
                     )
                }
            }

            item {
                val currentWeather = state.currentWeather
                if (currentWeather != null) {
                    WeatherDetailsBlock(
                        currentWeather = currentWeather,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
            }
        }
    }
}