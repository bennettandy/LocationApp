package uk.co.avsoftware.location.interactor

import android.Manifest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.first
import uk.co.avsoftware.locationdomain.model.LocationEvent
import uk.co.avsoftware.locationdomain.repository.AndroidLocationEventsRepository

class GetLocationInteractor(
    private val deviceLocationRepository: AndroidLocationEventsRepository
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    suspend fun invoke(): LocationEvent = deviceLocationRepository().first()
}
