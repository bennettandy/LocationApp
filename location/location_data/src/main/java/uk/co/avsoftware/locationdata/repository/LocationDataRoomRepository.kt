package uk.co.avsoftware.locationdata.repository

import kotlinx.coroutines.flow.Flow
import uk.co.avsoftware.locationdata.dao.LocationDao
import uk.co.avsoftware.locationdata.entities.Location
import javax.inject.Inject

class LocationDataRoomRepository @Inject constructor(
    private val locationDao: LocationDao
) {
    fun getLocationList(): Flow<List<Location>> = locationDao.getAllLocations()
}
