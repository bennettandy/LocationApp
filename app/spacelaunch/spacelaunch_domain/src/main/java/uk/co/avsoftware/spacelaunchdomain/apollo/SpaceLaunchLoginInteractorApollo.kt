package uk.co.avsoftware.spacelaunchdomain.apollo

import uk.co.avsoftware.spacelaunchdata.interactor.ApolloLoginInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.SpaceLaunchLoginInteractor
import javax.inject.Inject

internal class SpaceLaunchLoginInteractorApollo @Inject constructor(
    private val apolloLoginInteractor: ApolloLoginInteractor,
) : SpaceLaunchLoginInteractor {
    override suspend operator fun invoke(email: String): Boolean = apolloLoginInteractor.invoke(email)
}
