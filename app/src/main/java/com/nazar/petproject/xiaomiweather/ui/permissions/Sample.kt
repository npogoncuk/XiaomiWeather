package com.nazar.petproject.xiaomiweather.ui.permissions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


private val locationPermissions = listOf(
    android.Manifest.permission.ACCESS_COARSE_LOCATION,
    android.Manifest.permission.ACCESS_FINE_LOCATION,
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Sample(
    onLocationPermissionResult: (LocationPermissionStatus) -> Unit,
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        locationPermissions
    )

    if (locationPermissionsState.allPermissionsGranted) {
        onLocationPermissionResult(LocationPermissionStatus.GRANTED)
        return
    } else {
        Column {
            val allPermissionsRevoked =
                locationPermissionsState.permissions.size ==
                        locationPermissionsState.revokedPermissions.size

            val textToShow = if (!allPermissionsRevoked) {
                //locationPermissionsState.launchMultiplePermissionRequest()
                // If not all the permissions are revoked, it's because the user accepted the COARSE
                // location permission, but not the FINE one.
                "Yay! Thanks for letting me access your approximate location. " +
                        "But you know what would be great? If you allow me to know where you " +
                        "exactly are. Thank you!"
            } else if (locationPermissionsState.shouldShowRationale) {
                onLocationPermissionResult(LocationPermissionStatus.SHOW_RATIONALE)
                // Both location permissions have been denied
                "Getting your exact location is important for this app. " +
                        "Please grant us fine location. Thank you :D"
            } else {
                onLocationPermissionResult(LocationPermissionStatus.DENIED)
                // First time the user sees this feature or the user doesn't want to be asked again
                "This feature requires location permission"
            }

            val buttonText = if (!allPermissionsRevoked) {
                "Allow precise location"
            } else {
                "Request permissions"
            }

            Text(text = textToShow)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                locationPermissionsState.launchMultiplePermissionRequest()
            }) {
                Text(buttonText)
            }
        }
    }
}

enum class LocationPermissionStatus {
    GRANTED,
    DENIED,
    SHOW_RATIONALE,
}