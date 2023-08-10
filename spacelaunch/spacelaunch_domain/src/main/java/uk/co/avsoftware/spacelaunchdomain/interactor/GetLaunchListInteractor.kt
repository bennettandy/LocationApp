package uk.co.avsoftware.spacelaunchdomain.interactor

import uk.co.avsoftware.spacelaunchdomain.model.Launches

interface GetLaunchListInteractor {
    suspend operator fun invoke(): Launches?
}
