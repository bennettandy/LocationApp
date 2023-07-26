package uk.co.avsoftware.spacelaunchdomain.apollo

import uk.co.avsoftware.spacelaunchdata.TripBookingResponse
import uk.co.avsoftware.spacelaunchdata.interactor.ApolloBookTripInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.BookLaunchInteractor
import uk.co.avsoftware.spacelaunchdomain.model.LaunchBookingResponse
import javax.inject.Inject

internal class BookLaunchInteractorApollo @Inject constructor(
    private val apolloBookTripInteractor: ApolloBookTripInteractor
) : BookLaunchInteractor {
    override suspend operator fun invoke(launchId: String): LaunchBookingResponse =
        apolloBookTripInteractor.invoke(launchId).let {
                response ->
            when (response) {
                is TripBookingResponse.BookingSuccess -> LaunchBookingResponse.Success
                is TripBookingResponse.NotLoggedIn -> LaunchBookingResponse.NotLoggedIn
                else -> LaunchBookingResponse.Error
            }
        }
}
