package com.nazar.petproject.xiaomiweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.nazar.petproject.domain.settings.usecases.GetTemperatureUnitsUseCase
import com.nazar.petproject.xiaomiweather.ui.XiaomiWeatherApp
import com.nazar.petproject.xiaomiweather.ui.theme.XiaomiWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getTemperatureUnitsUseCase: GetTemperatureUnitsUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XiaomiWeatherTheme {
                XiaomiWeatherApp()
            }
        }

        lifecycleScope.launch {
            val l = getTemperatureUnitsUseCase.invoke()
            Log.d("MainActivity", "Temperature units: $l")
        }
    }
}