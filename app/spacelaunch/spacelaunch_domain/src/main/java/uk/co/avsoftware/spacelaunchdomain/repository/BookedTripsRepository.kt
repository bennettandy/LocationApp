package uk.co.avsoftware.spacelaunchdomain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.spacelaunchdata.repository.ApolloBookedTripsRepository
import uk.co.avsoftware.spacelaunchdomain.model.TripBookedResponse
import javax.inject.Inject

class BookedTripsRepository @Inject constructor(
    private val apolloBookedTripsRepository: ApolloBookedTripsRepository
) {
    fun bookedTripsFlow(): Flow<TripBookedResponse?> = apolloBookedTripsRepository.bookedTripsResponseFlow().map {
        it?.let {
            TripBookedResponse(it.numberOfTripsBooked)
        }
    }
}
