package com.nazar.petproject.xiaomiweather.ui.screens.composite_weather

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nazar.petproject.xiaomiweather.ui.Dimensions.DEFAULT_SMALL_PADDING
import com.nazar.petproject.xiaomiweather.ui.OneTimeUIEvent


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
    Scaffold(
        topBar = { CompositeWeatherTopAppBar() }
    ) { paddingValues ->

        val scrollState = rememberLazyListState()

        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            item { CurrentTemperatureBlock(
                modifier = Modifier.padding(DEFAULT_SMALL_PADDING),
                currentTemperature = 25,
                temperatureUnit = "°C",
                highLow = "High 25°C / Low 25°C",
                aqi = "AQI 25")
            }
        }
    }
}