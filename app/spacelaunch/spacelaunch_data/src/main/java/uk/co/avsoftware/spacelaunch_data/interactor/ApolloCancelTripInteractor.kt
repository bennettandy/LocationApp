package uk.co.avsoftware.spacelaunch_data.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.CancelTripMutation
import uk.co.avsoftware.spacelaunch_data.TokenRepository
import uk.co.avsoftware.spacelaunch_data.TripBookingResponse
import javax.inject.Inject

class ApolloCancelTripInteractor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val apolloClient: ApolloClient
){

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