package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nazar.petproject.xiaomiweather.ui.permissions.LocationPermissionStatus
import com.nazar.petproject.xiaomiweather.ui.permissions.RequestLocationPermission
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.ColumnWithAllWeatherBlocks
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.CompositeWeatherTopAppBar


@Composable
fun CompositeWeatherScreen(
    state: CompositeWeatherState,
    onIntent: (CompositeWeatherIntent) -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    if (state.shouldRequestLocationPermission) {
        RequestLocationPermission(
            modifier = Modifier
                .fillMaxSize(),
            onLocationPermissionResult = { locationPermissionStatus ->
                when (locationPermissionStatus) {
                    LocationPermissionStatus.GRANTED -> {
                        onIntent(CompositeWeatherIntent.OnRefreshData)
                    }
                    else -> {}
                }
            }
        )
        return
    }

    Scaffold(
        topBar = {
            CompositeWeatherTopAppBar(
                onPlusClick = {
                     // TODO(): implement adding new location
                },
                onShareClick = { /*TODO(): implement sharing weather screen  */ },
                onSettingsClick = { onNavigateToSettings() }
            )
        }
    ) { paddingValues ->


        val scrollState = rememberScrollState()
        val dailyWeather = state.dailyWeather
        val currentWeather = state.currentWeather

        if (dailyWeather == null || currentWeather == null) {
            return@Scaffold
        }

        ColumnWithAllWeatherBlocks(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(paddingValues),
            currentWeather = state.currentWeather,
            dailyWeather = state.dailyWeather,
        )
    }
}