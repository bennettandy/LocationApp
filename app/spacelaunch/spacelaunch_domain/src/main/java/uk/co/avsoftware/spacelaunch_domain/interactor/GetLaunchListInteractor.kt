package uk.co.avsoftware.spacelaunch_domain.interactor

import uk.co.avsoftware.spacelaunch_data.interactor.ApolloGetLaunchListInteractor
import uk.co.avsoftware.spacelaunch_domain.mapper.mapToDomain
import uk.co.avsoftware.spacelaunch_domain.model.Launches
import javax.inject.Inject

class GetLaunchListInteractor @Inject constructor(
    private val apolloGetLaunchListInteractor: ApolloGetLaunchListInteractor
) {
    suspend operator fun invoke(): Launches? =
        apolloGetLaunchListInteractor()?.mapToDomain()
}