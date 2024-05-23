package com.nazar.petproject.xiaomiweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.weather.CurrentWeatherUseCase
import com.nazar.petproject.domain.weather.entities.current_weather.ICurrentWeatherValues
import com.nazar.petproject.xiaomiweather.ui.theme.XiaomiWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XiaomiWeatherTheme {
        Greeting("Android")
    }
}