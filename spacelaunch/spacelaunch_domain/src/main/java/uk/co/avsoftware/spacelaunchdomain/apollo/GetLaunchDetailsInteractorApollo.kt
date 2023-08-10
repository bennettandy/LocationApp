package uk.co.avsoftware.spacelaunchdomain.apollo

import uk.co.avsoftware.spacelaunchdata.interactor.ApolloGetLaunchDetailInteractor
import uk.co.avsoftware.spacelaunchdata.interactor.LaunchDetailsResponse
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchDetailsInteractor
import uk.co.avsoftware.spacelaunchdomain.mapper.mapToDomain
import uk.co.avsoftware.spacelaunchdomain.model.SpaceLaunchDetailResponse
import javax.inject.Inject

internal class GetLaunchDetailsInteractorApollo @Inject constructor(
    private val getLaunchDetailInteractor: ApolloGetLaunchDetailInteractor
) : GetLaunchDetailsInteractor {
    override suspend operator fun invoke(launchId: String): SpaceLaunchDetailResponse =
        when (val response: LaunchDetailsResponse = getLaunchDetailInteractor.invoke(launchId)) {
            is LaunchDetailsResponse.Success -> {
                SpaceLaunchDetailResponse.Success(
                    response.data?.mapToDomain()
                )
            }

            is LaunchDetailsResponse.ApplicationError ->
                SpaceLaunchDetailResponse.DomainError("Application Error Failed to load details ${response.message}")

            is LaunchDetailsResponse.ProtocolError ->
                SpaceLaunchDetailResponse.DomainError("Protocol Error Failed to load details ${response.message}")
        }
}
