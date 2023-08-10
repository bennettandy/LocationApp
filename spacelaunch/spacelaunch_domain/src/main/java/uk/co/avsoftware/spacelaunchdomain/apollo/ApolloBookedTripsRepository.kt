package uk.co.avsoftware.spacelaunchdomain.apollo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.spacelaunchdata.repository.ApolloBookedTripsRepository
import uk.co.avsoftware.spacelaunchdomain.model.TripBookedResponse
import uk.co.avsoftware.spacelaunchdomain.repository.BookedTripsRepository
import javax.inject.Inject

internal class ApolloBookedTripsRepository @Inject constructor(
    private val apolloBookedTripsRepository: ApolloBookedTripsRepository
) : BookedTripsRepository {
    override fun bookedTripsFlow(): Flow<TripBookedResponse?> = apolloBookedTripsRepository.bookedTripsResponseFlow().map {
        it?.let {
            TripBookedResponse(it.numberOfTripsBooked)
        }
    }
}
