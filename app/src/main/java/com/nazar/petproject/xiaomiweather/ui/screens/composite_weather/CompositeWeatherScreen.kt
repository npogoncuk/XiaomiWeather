package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nazar.petproject.xiaomiweather.ui.permissions.LocationPermissionStatus
import com.nazar.petproject.xiaomiweather.ui.permissions.RequestLocationPermission
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.ColumnWithAllWeatherBlocks
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.components.CompositeWeatherTopAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompositeWeatherScreen(
    state: CompositeWeatherState,
    onIntent: (CompositeWeatherIntent) -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    SideEffect {
        Log.d("CompositeWeatherScreen", "state: $state")
    }
    val pullRefreshState = rememberPullRefreshState(state.isRefreshing, onRefresh = {
        onIntent(CompositeWeatherIntent.OnRefreshData)
    })

    val scrollState = rememberScrollState()


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

            val dailyWeather = state.dailyWeather
            val currentWeather = state.currentWeather

            if (dailyWeather != null && currentWeather != null) {
                ColumnWithAllWeatherBlocks(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                        .fillMaxWidth(),
                    currentWeather = currentWeather,
                    dailyWeather = dailyWeather,
                )
            }

        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .verticalScroll(scrollState)
        ) {
            PullRefreshIndicator(state.isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}