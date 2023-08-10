package uk.co.avsoftware.spacelaunchdomain.interactor

import uk.co.avsoftware.spacelaunchdomain.model.LaunchBookingResponse

interface CancelLaunchBookingInteractor {
    suspend operator fun invoke(launchId: String): LaunchBookingResponse
}
