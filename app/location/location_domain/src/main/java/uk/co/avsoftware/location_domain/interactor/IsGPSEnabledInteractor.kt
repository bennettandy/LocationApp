package uk.co.avsoftware.location.interactor

import android.location.LocationManager

class IsGPSEnabledInteractor(private val locationManager: LocationManager) {
    operator fun invoke(): Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}
