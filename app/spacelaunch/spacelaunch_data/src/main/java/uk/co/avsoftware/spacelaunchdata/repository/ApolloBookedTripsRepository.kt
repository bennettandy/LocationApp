package uk.co.avsoftware.spacelaunchdata.repository

import com.apollographql.apollo3.ApolloClient
import com.example.rocketreserver.TripsBookedSubscription
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.spacelaunchdata.BookedTripResponse
import javax.inject.Inject

class ApolloBookedTripsRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    fun bookedTripsResponseFlow(): Flow<BookedTripResponse?> =
        apolloClient.subscription(TripsBookedSubscription()).toFlow()
            .map {
                it.data?.let { data ->
                    // not null
                    BookedTripResponse(
                        data.tripsBooked
                    )
                }
            }
}
