package com.nazar.petproject.xiaomiweather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.nazar.petproject.domain.location.use_cases.GetCurrentLocationUseCase
import com.nazar.petproject.domain.onSuccess
import com.nazar.petproject.xiaomiweather.ui.XiaomiWeatherApp
import com.nazar.petproject.xiaomiweather.ui.theme.XiaomiWeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MainActivity", "Permission granted")
                lifecycleScope.launch {
                    getCurrentLocationUseCase().collect { result ->
                        Log.d("MainActivity", "result: ${result}")

                        result.onSuccess {
                            Log.d("MainActivity", "Location: ${this.data}")
                        }
                    }
                }
            } else {
                Log.d("MainActivity", "Permission denied")
            }
        }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Show an explanation to the user. After the user
                // sees the explanation, try again to request the permission.
            }
            else -> {
                // You can directly ask for the permission.
                requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XiaomiWeatherTheme {
                XiaomiWeatherApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "Permission granted")
        lifecycleScope.launch {
            getCurrentLocationUseCase().collect { result ->
                Log.d("MainActivity", "result: ${result}")

                result.onSuccess {
                    Log.d("MainActivity", "Location: ${this.data}")
                }
            }
        }
    }

}