package uk.co.avsoftware.spacelaunch_domain.interactor

import uk.co.avsoftware.spacelaunch_data.interactor.ApolloGetLaunchDetailInteractor
import uk.co.avsoftware.spacelaunch_data.interactor.LaunchDetailsResponse
import uk.co.avsoftware.spacelaunch_domain.mapper.mapToDomain
import uk.co.avsoftware.spacelaunch_domain.model.SpaceLaunchDetailResponse
import javax.inject.Inject

class GetLaunchDetailsInteractor @Inject constructor(
    private val getLaunchDetailInteractor: ApolloGetLaunchDetailInteractor
) {
    suspend operator fun invoke(launchId: String): SpaceLaunchDetailResponse =
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