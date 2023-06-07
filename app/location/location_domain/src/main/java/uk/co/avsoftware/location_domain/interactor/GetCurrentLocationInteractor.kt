package uk.co.avsoftware.location_domain.interactor

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.first
import uk.co.avsoftware.location_domain.repository.LocationEventsRepository

class GetCurrentLocationInteractor(
    private val locationEventsRepository: LocationEventsRepository
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    suspend operator fun invoke(): Location = locationEventsRepository().first()
}
