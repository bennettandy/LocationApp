package uk.co.avsoftware.spacelaunchdomain.interactor

interface SpaceLaunchLoginInteractor {
    suspend operator fun invoke(email: String): Boolean
}
