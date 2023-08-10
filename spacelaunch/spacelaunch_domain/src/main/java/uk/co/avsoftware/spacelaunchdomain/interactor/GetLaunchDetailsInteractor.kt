package uk.co.avsoftware.spacelaunchdomain.interactor

import uk.co.avsoftware.spacelaunchdomain.model.SpaceLaunchDetailResponse

interface GetLaunchDetailsInteractor {
    suspend operator fun invoke(launchId: String): SpaceLaunchDetailResponse
}
