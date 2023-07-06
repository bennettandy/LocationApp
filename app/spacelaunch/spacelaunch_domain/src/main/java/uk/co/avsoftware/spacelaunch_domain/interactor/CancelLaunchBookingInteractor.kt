package uk.co.avsoftware.spacelaunch_domain.interactor

import uk.co.avsoftware.spacelaunch_data.TripBookingResponse
import uk.co.avsoftware.spacelaunch_data.interactor.ApolloCancelTripInteractor
import uk.co.avsoftware.spacelaunch_domain.model.LaunchBookingResponse
import javax.inject.Inject

class CancelLaunchBookingInteractor @Inject constructor(
    private val apolloCancelTripInteractor: ApolloCancelTripInteractor
){
    suspend operator fun invoke(launchId: String): LaunchBookingResponse =
        apolloCancelTripInteractor.invoke(launchId).let {
                response ->
            when (response){
                is TripBookingResponse.CancelSuccess -> LaunchBookingResponse.Success
                is TripBookingResponse.NotLoggedIn -> LaunchBookingResponse.NotLoggedIn
                else -> LaunchBookingResponse.Error
            }
        }
}