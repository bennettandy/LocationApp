package uk.co.avsoftware.locationdomain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.locationdata.dao.LocationDao
import uk.co.avsoftware.locationdomain.model.LocationEvent
import javax.inject.Inject

class LocationDataRepository @Inject constructor(
    private val locationDao: LocationDao
) {
    fun getLocationList(): Flow<List<LocationEvent>> = locationDao.getAllLocations().map { list ->
        list.map { location ->
            // Map to Domain Object
            LocationEvent(
                latitude = location.latitude,
                longitude = location.longitude,
            )
        }
    }
}
