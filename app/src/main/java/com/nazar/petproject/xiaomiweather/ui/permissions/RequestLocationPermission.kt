package com.nazar.petproject.xiaomiweather.ui.permissions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch

private val locationPermissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val activity = LocalContext.current as Activity

    var locationPermissionsGranted by remember { mutableStateOf(false) }
    var shouldShowPermissionRationale by remember {
        mutableStateOf(
            activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    var shouldDirectUserToApplicationSettings by remember {
        mutableStateOf(false)
    }


    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted
            }

            if (!locationPermissionsGranted) {
                shouldShowPermissionRationale =
                    activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            } else {
                onPermissionGranted()
            }
            shouldDirectUserToApplicationSettings = !shouldShowPermissionRationale && !locationPermissionsGranted
        })

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START &&
                !locationPermissionsGranted &&
                !shouldShowPermissionRationale) {
                locationPermissionLauncher.launch(locationPermissions)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    )

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (shouldShowPermissionRationale) {
        LaunchedEffect(Unit) {
            scope.launch {
                val userAction = snackbarHostState.showSnackbar(
                    message ="Please authorize location permissions",
                    actionLabel = "Approve",
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
                when (userAction) {
                    SnackbarResult.ActionPerformed -> {
                        shouldShowPermissionRationale = false
                        locationPermissionLauncher.launch(locationPermissions)
                    }
                    SnackbarResult.Dismissed -> {
                        shouldShowPermissionRationale = false
                    }
                }
            }
        }
    }
    if (shouldDirectUserToApplicationSettings) {
        activity.openApplicationSettings()
    }


}
private fun Activity.openApplicationSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).also {
        startActivity(it)
    }
}