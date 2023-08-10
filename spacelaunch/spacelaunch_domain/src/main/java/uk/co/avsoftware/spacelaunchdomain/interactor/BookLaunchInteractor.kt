package uk.co.avsoftware.spacelaunchdomain.interactor

import uk.co.avsoftware.spacelaunchdomain.model.LaunchBookingResponse

interface BookLaunchInteractor {
    suspend operator fun invoke(launchId: String): LaunchBookingResponse
}
