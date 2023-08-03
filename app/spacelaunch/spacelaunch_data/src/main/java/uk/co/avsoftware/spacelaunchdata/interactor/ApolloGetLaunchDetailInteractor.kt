package uk.co.avsoftware.spacelaunchdata.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import uk.co.avsoftware.spacelaunchdata.LaunchDetailsQuery
import javax.inject.Inject

class ApolloGetLaunchDetailInteractor @Inject constructor(
    private val apolloClient: ApolloClient,
) {
    suspend operator fun invoke(launchId: String): LaunchDetailsResponse =
        loadDetails(launchId)

    private suspend fun loadDetails(launchId: String) = try {
        val response = apolloClient.query(LaunchDetailsQuery(launchId)).execute()
        if (response.hasErrors()) {
            LaunchDetailsResponse.ApplicationError(response.errors?.first()?.message)
        } else {
            LaunchDetailsResponse.Success(response.data?.launch)
        }
    } catch (e: ApolloException) {
        LaunchDetailsResponse.ProtocolError(e.message)
    }
}

sealed interface LaunchDetailsResponse {
    data class ProtocolError(val message: String?) : LaunchDetailsResponse
    data class ApplicationError(val message: String?) : LaunchDetailsResponse
    data class Success(val data: LaunchDetailsQuery.Launch?) : LaunchDetailsResponse
}
