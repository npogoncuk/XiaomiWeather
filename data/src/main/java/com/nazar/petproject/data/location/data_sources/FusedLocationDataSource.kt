package com.nazar.petproject.data.location.data_sources

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.TimeZone
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nazar.petproject.domain.location.entities.ILocation
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class FusedLocationDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    suspend fun getCurrentLocation(): ILocation {
        if (!permissionsGranted) {
            throw LocationPermissionNotGrantedException()
        }

        val location = try {
            fusedLocationClient.lastLocation.await().also {
                if (it == null) {
                    throw SecurityException()
                }
            }
        } catch (e: SecurityException) {
            throw LocationPermissionNotGrantedException()
        }

        val geocoder = Geocoder(context, Locale.getDefault())

        val address = geocoder.getAddress(location.latitude, location.longitude)

        val cityName = address.locality
        val timezone = TimeZone.getDefault().id

        return object : ILocation {
            override val latitude: Double = location.latitude
            override val longitude: Double = location.longitude
            override val timeZoneID: String = timezone
            override val city: String = cityName

            override fun toString(): String {
                return "Location(latitude=$latitude, longitude=$longitude, timeZoneID='$timeZoneID', city='$city')"
            }
        }
    }

    private suspend fun Geocoder.getAddress(
        latitude: Double,
        longitude: Double,
    ): Address = suspendCoroutine { continuation ->

        fun Continuation<Address>.resumeWithAddressOrException(address: Address?) {
            address?.let { resume(it) } ?: resumeWithException(AddressNullException())
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getFromLocation(latitude, longitude, 1) { addresses ->
                    continuation.resumeWithAddressOrException(addresses.firstOrNull())
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = getFromLocation(latitude, longitude, 1)
                continuation.resumeWithAddressOrException(addresses?.firstOrNull())
            }
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    private val permissionsGranted: Boolean
        get() {
            val accessFineLocationGranted = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val accessCoarseLocationGranted = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            return accessFineLocationGranted && accessCoarseLocationGranted
        }
}

class LocationPermissionNotGrantedException : Exception()
class AddressNullException : Exception()