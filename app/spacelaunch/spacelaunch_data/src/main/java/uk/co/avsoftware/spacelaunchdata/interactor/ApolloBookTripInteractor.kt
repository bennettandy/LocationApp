package uk.co.avsoftware.spacelaunchdata.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.BookTripMutation
import uk.co.avsoftware.spacelaunchdata.TokenRepository
import uk.co.avsoftware.spacelaunchdata.TripBookingResponse
import javax.inject.Inject

class ApolloBookTripInteractor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val apolloClient: ApolloClient
) {
    suspend operator fun invoke(launchId: String): TripBookingResponse {
        if (tokenRepository.getToken() == null) {
            return TripBookingResponse.NotLoggedIn
        }

        val response = try {
            apolloClient.mutation(BookTripMutation(launchId)).execute()
        } catch (e: ApolloException) {
            return TripBookingResponse.Error
        }

        if (response.hasErrors()) return TripBookingResponse.Error

        return TripBookingResponse.BookingSuccess(
            response.data?.bookTrips
        )
    }
}
