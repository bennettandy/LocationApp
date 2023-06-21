package uk.co.avsoftware.location.interactor

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.first
import uk.co.avsoftware.locationdomain.model.LocationEvent
import uk.co.avsoftware.locationdomain.repository.LocationEventsRepository

class GetLocationInteractor(
    private val deviceLocationRepository: LocationEventsRepository
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    suspend fun invoke(): LocationEvent = deviceLocationRepository().first()
}
