package com.nazar.petproject.xiaomiweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeatherValues
import com.nazar.petproject.xiaomiweather.ui.theme.XiaomiWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
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
            weatherUseCase.getCurrentWeather().collect {
                Log.d("MainActivity", "getCurrentWeather: $it")
                when (it) {
                    is IResult.Success -> {
                        val weather = it.data
                        Log.d("MainActivity", "getCurrentWeather: $weather")
                        val currentWeather = weather
                        Log.d("MainActivity", "currentWeather: $currentWeather")
                        Log.d("MainActivity", "rainUnit: ${currentWeather.propertyToUnitMap["rain"]}")
                        Log.d("MainActivity", "rainUnitReflect: ${currentWeather.getUnit(ICurrentWeatherValues::rain)}")

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
    NavHost(navController = navController, startDestination = Destination.Main) {
        val modifier = Modifier.fillMaxSize()
        composable<Destination.Main> {
            CommonScreen(modifier = modifier, name = "Main") {
                navController.navigate(Destination.Details)
            }
        }

        composable<Destination.Details> {
            CommonScreen(modifier = modifier, name = "Details") {
                navController.navigate(Destination.Main)
            }
        }
    }
}


@Composable
fun CommonScreen(
    modifier: Modifier = Modifier,
    name: String = "",
    onButtonClick: () -> Unit = {}
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(32.dp)
        .background(Color.Blue),

    ) {
        Text(
            text = "Hello $name!",
            fontSize = 48.sp,
        )
        Button(
            onClick = { onButtonClick.invoke() }) {
            Text(text = "Go there")
        }
    }
}

@Serializable
sealed interface Destination {
    @Serializable
    data object Main : Destination

    @Serializable
    data object Details : Destination
}