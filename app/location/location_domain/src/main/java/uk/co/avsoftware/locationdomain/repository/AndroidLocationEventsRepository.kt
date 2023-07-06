package uk.co.avsoftware.locationdomain.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.locationdata.repository.AndroidLocationEventsProvider
import uk.co.avsoftware.locationdomain.model.LocationEvent
import javax.inject.Inject

class AndroidLocationEventsRepository @Inject constructor(
    private val androidLocationEventsProvider: AndroidLocationEventsProvider
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    operator fun invoke(): Flow<LocationEvent> = androidLocationEventsProvider.invoke().map {
        LocationEvent(
            latitude = it.latitude,
            longitude = it.longitude
        )
    }
}
