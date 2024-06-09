package com.nazar.petproject.xiaomiweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
import com.nazar.petproject.domain.weather.CurrentWeatherUseCase
import com.nazar.petproject.xiaomiweather.ui.XiaomiWeatherApp
import com.nazar.petproject.xiaomiweather.ui.screens.composite_weather.CompositeWeatherViewModel
import com.nazar.petproject.xiaomiweather.ui.theme.XiaomiWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XiaomiWeatherTheme {
                XiaomiWeatherApp()
            }
        }
    }
}