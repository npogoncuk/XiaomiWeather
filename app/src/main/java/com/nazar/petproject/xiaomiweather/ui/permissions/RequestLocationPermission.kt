package com.nazar.petproject.xiaomiweather.ui.permissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.nazar.petproject.xiaomiweather.R


private val locationPermissions = listOf(
    android.Manifest.permission.ACCESS_COARSE_LOCATION,
    android.Manifest.permission.ACCESS_FINE_LOCATION,
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    modifier: Modifier = Modifier,
    onLocationPermissionResult: (LocationPermissionStatus) -> Unit,
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        locationPermissions
    )

    if (locationPermissionsState.allPermissionsGranted) {
        onLocationPermissionResult(LocationPermissionStatus.GRANTED)
        return
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val allPermissionsRevoked =
            locationPermissionsState.permissions.size ==
                    locationPermissionsState.revokedPermissions.size

        val textToShowRes = if (!allPermissionsRevoked) {
            // If not all the permissions are revoked, it's because the user accepted the COARSE
            // location permission, but not the FINE one.
            R.string.thanks_for_approximate_location
        } else if (locationPermissionsState.shouldShowRationale) {
            onLocationPermissionResult(LocationPermissionStatus.SHOW_RATIONALE)
            // Both location permissions have been denied
            R.string.location_denied_please_grant_permissions
        } else {
            onLocationPermissionResult(LocationPermissionStatus.DENIED)
            // First time the user sees this feature or the user doesn't want to be asked again
            R.string.this_feature_requires_location_permission
        }

        val buttonTextRes = if (!allPermissionsRevoked) {
            R.string.allow_precise_location
        } else {
            R.string.allow_permissions
        }

        Text(text = stringResource(id = textToShowRes))
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            locationPermissionsState.launchMultiplePermissionRequest()
        }) {
            Text(stringResource(id = buttonTextRes))
        }
    }
}

enum class LocationPermissionStatus {
    GRANTED,
    DENIED,
    SHOW_RATIONALE,
}