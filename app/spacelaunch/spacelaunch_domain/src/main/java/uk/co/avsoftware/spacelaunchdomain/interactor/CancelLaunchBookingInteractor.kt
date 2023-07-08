package uk.co.avsoftware.spacelaunchdomain.interactor

import uk.co.avsoftware.spacelaunchdata.TripBookingResponse
import uk.co.avsoftware.spacelaunchdata.interactor.ApolloCancelTripInteractor
import uk.co.avsoftware.spacelaunchdomain.model.LaunchBookingResponse
import javax.inject.Inject

class CancelLaunchBookingInteractor @Inject constructor(
    private val apolloCancelTripInteractor: ApolloCancelTripInteractor
) {
    suspend operator fun invoke(launchId: String): LaunchBookingResponse =
        apolloCancelTripInteractor.invoke(launchId).let {
                response ->
            when (response) {
                is TripBookingResponse.CancelSuccess -> LaunchBookingResponse.Success
                is TripBookingResponse.NotLoggedIn -> LaunchBookingResponse.NotLoggedIn
                else -> LaunchBookingResponse.Error
            }
        }
}
