package uk.co.avsoftware.location.interactor

import android.Manifest
import android.location.Location
import android.location.LocationManager
import androidx.annotation.RequiresPermission

class GetLastLocationInteractor(
    private val locationManager: LocationManager
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    operator fun invoke(): Location? = locationManager.getLastKnownLocation(
        LocationManager.GPS_PROVIDER
    )
}
