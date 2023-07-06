package uk.co.avsoftware.locationdomain.interactor

import android.Manifest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.first
import uk.co.avsoftware.locationdomain.model.LocationEvent
import uk.co.avsoftware.locationdomain.repository.AndroidLocationEventsRepository

class GetCurrentLocationInteractor(
    private val locationEventsRepository: AndroidLocationEventsRepository
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    suspend operator fun invoke(): LocationEvent = locationEventsRepository().first()
}
