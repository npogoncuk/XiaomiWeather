package com.nazar.petproject.xiaomiweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeatherValues
import com.nazar.petproject.xiaomiweather.ui.Destination
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.CompositeWeatherScreen
import com.nazar.petproject.xiaomiweather.ui.theme.XiaomiWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var weatherUseCase: CurrentWeatherUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XiaomiWeatherTheme {
                WeatherApp()
            }
        }

        lifecycleScope.launch {
            delay(5000)
            weatherUseCase.invoke().collect {
                Log.d("MainActivity", "getCurrentWeather: $it")
                when (it) {
                    is IResult.Success -> {
                        val weather = it.data
                        Log.d("MainActivity", "getCurrentWeather: $weather")
                        Log.d("MainActivity", "currentWeather: $weather")
                        Log.d("MainActivity", "rainUnit: ${weather.propertyToUnitMap["rain"]}")
                        Log.d("MainActivity", "rainUnitReflect: ${weather.getUnit(ICurrentWeatherValues::rain)}")

                    }
                    else -> {}
                }
            }

        }
    }
}

@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.CompositeWeather) {
        composable<Destination.CompositeWeather> {
            CompositeWeatherScreen(navController = navController)
        }
        composable<Destination.Settings> {

        }
    }
}

