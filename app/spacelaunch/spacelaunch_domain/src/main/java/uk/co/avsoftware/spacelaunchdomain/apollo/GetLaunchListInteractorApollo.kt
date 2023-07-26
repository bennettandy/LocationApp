package uk.co.avsoftware.spacelaunchdomain.apollo

import uk.co.avsoftware.spacelaunchdata.interactor.ApolloGetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.mapper.mapToDomain
import uk.co.avsoftware.spacelaunchdomain.model.Launches
import javax.inject.Inject

internal class GetLaunchListInteractorApollo @Inject constructor(
    private val apolloGetLaunchListInteractor: ApolloGetLaunchListInteractor
) : GetLaunchListInteractor {
    override suspend operator fun invoke(): Launches? =
        apolloGetLaunchListInteractor()?.mapToDomain()
}
