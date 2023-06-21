package uk.co.avsoftware.locationdomain.repository

import android.Manifest
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.locationdata.repository.AndroidLocationEventsRepository
import uk.co.avsoftware.locationdomain.model.LocationEvent
import javax.inject.Inject

class LocationEventsRepository @Inject constructor(
    private val androidLocationEventsRepository: AndroidLocationEventsRepository
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    operator fun invoke(): Flow<LocationEvent> = androidLocationEventsRepository.invoke().map {
        LocationEvent(
            latitude = it.latitude,
            longitude = it.longitude
        )
    }
}
