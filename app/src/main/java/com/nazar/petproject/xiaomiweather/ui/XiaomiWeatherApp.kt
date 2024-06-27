package com.nazar.petproject.xiaomiweather.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.CompositeWeatherScreen
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.CompositeWeatherViewModel
import com.nazar.petproject.xiaomiweather.ui.screens.settings.SettingsScreen

@Composable
fun XiaomiWeatherApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.CompositeWeather) {
        composable<Destination.CompositeWeather> {

            val viewModel = hiltViewModel<CompositeWeatherViewModel>()
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                viewModel.oneTimeUIEvent.collect { uiEvent ->
                    val message = (uiEvent as? OneTimeUIEvent.ShowToast)?.message.toString()
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }

            val state by viewModel.weatherState.collectAsState()

            CompositeWeatherScreen(
                state = state,
                onIntent = viewModel::processIntent,
                onNavigateToSettings = { navController.navigate(Destination.Settings) }
            )
        }
        composable<Destination.Settings> {
            SettingsScreen(navController = navController)
        }
    }
}