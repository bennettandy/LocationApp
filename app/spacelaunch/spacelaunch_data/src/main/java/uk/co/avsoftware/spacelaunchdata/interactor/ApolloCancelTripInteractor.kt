package uk.co.avsoftware.spacelaunchdata.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import uk.co.avsoftware.spacelaunchdata.CancelTripMutation
import uk.co.avsoftware.spacelaunchdata.TokenRepository
import uk.co.avsoftware.spacelaunchdata.TripBookingResponse
import javax.inject.Inject

class ApolloCancelTripInteractor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val apolloClient: ApolloClient
) {

    suspend operator fun invoke(launchId: String): TripBookingResponse {
        if (tokenRepository.getToken() == null) {
            return TripBookingResponse.NotLoggedIn
        }

        val response = try {
            apolloClient.mutation(CancelTripMutation(launchId)).execute()
        } catch (e: ApolloException) {
            return TripBookingResponse.Error
        }

        if (response.hasErrors()) return TripBookingResponse.Error

        return TripBookingResponse.CancelSuccess(
            response.data
        )
    }
}
