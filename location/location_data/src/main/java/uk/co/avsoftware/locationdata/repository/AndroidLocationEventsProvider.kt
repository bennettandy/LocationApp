package uk.co.avsoftware.locationdata.repository

import android.Manifest
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AndroidLocationEventsProvider @Inject constructor(
    private val locationManager: LocationManager
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    operator fun invoke(): Flow<Location> = callbackFlow {
        val locationListener = LocationListener { trySend(it) }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            UPDATE_INTERVAL_MS,
            MIN_DISTANCE_BETWEEN_UPDATES_M,
            locationListener,
        )
        awaitClose {
            locationManager.removeUpdates(locationListener)
        }
    }

    companion object {
        // Minimum time interval between updates (in milliseconds)
        private const val UPDATE_INTERVAL_MS = 2000L

        // Minimum distance between updates (in meters)
        private const val MIN_DISTANCE_BETWEEN_UPDATES_M = 10.0f
    }
}
