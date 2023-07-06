package uk.co.avsoftware.spacelaunch_domain.interactor

import uk.co.avsoftware.spacelaunch_data.interactor.ApolloLoginInteractor
import javax.inject.Inject

class SpaceLaunchLoginInteractor @Inject constructor(
    private val apolloLoginInteractor: ApolloLoginInteractor
) {
    suspend operator fun invoke(email: String): Boolean = apolloLoginInteractor.invoke(email)
}
