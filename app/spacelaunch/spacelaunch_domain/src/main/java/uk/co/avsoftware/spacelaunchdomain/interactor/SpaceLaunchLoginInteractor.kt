package uk.co.avsoftware.spacelaunchdomain.interactor

import uk.co.avsoftware.spacelaunchdata.interactor.ApolloLoginInteractor
import javax.inject.Inject

class SpaceLaunchLoginInteractor @Inject constructor(
    private val apolloLoginInteractor: ApolloLoginInteractor
) {
    suspend operator fun invoke(email: String): Boolean = apolloLoginInteractor.invoke(email)
}
