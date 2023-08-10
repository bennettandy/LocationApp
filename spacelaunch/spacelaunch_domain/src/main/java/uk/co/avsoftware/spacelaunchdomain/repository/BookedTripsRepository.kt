package uk.co.avsoftware.spacelaunchdomain.repository

import kotlinx.coroutines.flow.Flow
import uk.co.avsoftware.spacelaunchdomain.model.TripBookedResponse

interface BookedTripsRepository {
    fun bookedTripsFlow(): Flow<TripBookedResponse?>
}
