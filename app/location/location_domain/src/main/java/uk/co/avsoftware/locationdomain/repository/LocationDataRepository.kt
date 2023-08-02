package uk.co.avsoftware.locationdomain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.locationdata.repository.LocationDataRoomRepository
import uk.co.avsoftware.locationdomain.model.LocationEvent
import javax.inject.Inject

class LocationDataRepository @Inject constructor(
    private val locationDataRoomRepository: LocationDataRoomRepository,
) {
    fun getLocationList(): Flow<List<LocationEvent>> = locationDataRoomRepository.getLocationList().map { list ->
        list.map { location ->
            // Map to Domain Object
            LocationEvent(
                latitude = location.latitude,
                longitude = location.longitude,
            )
        }
    }
}
