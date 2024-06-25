package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeatherValues
import com.nazar.petproject.xiaomiweather.ui.Destination
import com.nazar.petproject.xiaomiweather.ui.Dimensions
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent
import com.nazar.petproject.xiaomiweather.ui.permissions.LocationPermissionStatus
import com.nazar.petproject.xiaomiweather.ui.permissions.RequestLocationPermission
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.CompositeWeatherTopAppBar
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.CurrentTemperatureBlock
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.FiveDayForecastBlock
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.SunriseSunsetInfoBlock
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.WeatherDetailsBlock
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.WindInfoBlock


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

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CompositeWeatherTopAppBar(
                onPlusClick = {
                     // TODO(): implement adding new location
                },
                onShareClick = { /*TODO(): implement sharing weather screen  */ },
                onSettingsClick = { navController.navigate(Destination.Settings) }
            )
        }
    ) { paddingValues ->

        if (state.shouldRequestLocationPermission) {
            RequestLocationPermission(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onLocationPermissionResult = { locationPermissionStatus ->
                    when (locationPermissionStatus) {
                        LocationPermissionStatus.GRANTED -> {
                            viewModel.reloadData()
                        }
                        LocationPermissionStatus.SHOW_RATIONALE -> {
                            /*scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please grant us fine location. Thank you :D",
                                    actionLabel = "Request permissions",
                                    duration = SnackbarHostState.Duration.Short
                                )
                            }*/
                        }
                        LocationPermissionStatus.DENIED -> {
                            /*scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "This feature requires location permission",
                                    actionLabel = "Request permissions",
                                    duration = SnackbarHostState.Duration.Short
                                )
                            }*/
                        }
                    }
                }
            )
        }
        val scrollState = rememberScrollState()
        val dailyWeather = state.dailyWeather
        val currentWeather = state.currentWeather

        if (dailyWeather == null || currentWeather == null) {
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            val temperatureUnit = currentWeather.propertyToUnitMap["temperature2m"] ?: ""
            val dailyWeatherToday = dailyWeather.values.oneDayWeatherList.first()


            CurrentTemperatureBlock(
                modifier = Modifier.padding(Dimensions.DEFAULT_SMALL_PADDING),
                currentTemperature = currentWeather.values.temperature,
                temperatureUnit = temperatureUnit,
                highLow = "High ${dailyWeatherToday.temperatureMax}$temperatureUnit / Low ${dailyWeatherToday.temperatureMin}$temperatureUnit",
                aqi = "??"
            )

            FiveDayForecastBlock(
                dailyWeatherList = dailyWeather.values.oneDayWeatherList,
                modifier = Modifier.padding(Dimensions.DEFAULT_SMALL_PADDING)
            )

            Row(
                modifier = Modifier
                    .background(color = Color(0xFF86CDF0))
                    .height(200.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    WindInfoBlock(
                        windSpeed = currentWeather.values.windSpeed10m.toInt(),
                        windSpeedUnit = currentWeather.getUnit(ICurrentWeatherValues::windSpeed10m).toString(),
                        windDirection = currentWeather.values.windDirection10m,
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dimensions.DEFAULT_SMALL_PADDING)
                    )
                    SunriseSunsetInfoBlock(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(Dimensions.DEFAULT_SMALL_PADDING)
                    )
                    /*
                    WindInfoBlock(
                        windSpeed = currentWeather.values.windSpeed10m.toInt(),
                        windSpeedUnit = currentWeather.getUnit(ICurrentWeatherValues::windSpeed10m).toString(),
                        windDirection = currentWeather.values.windDirection10m,
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dimensions.DEFAULT_SMALL_PADDING)
                    )*/
                }
                WeatherDetailsBlock(
                    currentWeather = currentWeather,
                    modifier = Modifier
                        .background(color = Color(0xFFFFCDF0))
                        .padding(Dimensions.DEFAULT_SMALL_PADDING)
                        .height(IntrinsicSize.Max)
                )
            }


            SunriseSunsetInfoBlock(
                Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.DEFAULT_SMALL_PADDING)
            )
        }
    }
}