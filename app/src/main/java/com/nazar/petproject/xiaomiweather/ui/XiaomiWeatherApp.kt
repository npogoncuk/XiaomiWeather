package com.nazar.petproject.xiaomiweather.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.CompositeWeatherScreen

@Composable
fun XiaomiWeatherApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.CompositeWeather) {
        composable<Destination.CompositeWeather> {
            CompositeWeatherScreen(navController = navController)
        }
        composable<Destination.Settings> {

        }
    }
}