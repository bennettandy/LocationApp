package uk.co.avsoftware.spacelaunchdomain.interactor

import uk.co.avsoftware.spacelaunchdata.interactor.ApolloGetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.mapper.mapToDomain
import uk.co.avsoftware.spacelaunchdomain.model.Launches
import javax.inject.Inject

class GetLaunchListInteractor @Inject constructor(
    private val apolloGetLaunchListInteractor: ApolloGetLaunchListInteractor
) {
    suspend operator fun invoke(): Launches? =
        apolloGetLaunchListInteractor()?.mapToDomain()
}
