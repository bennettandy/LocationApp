package uk.co.avsoftware.spacelaunchdata.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.rocketreserver.LaunchListQuery
import javax.inject.Inject

class ApolloGetLaunchListInteractor @Inject constructor(
    private val apolloClient: ApolloClient
) {

    // was used in initial compose remember() state
    var cursor: String? = null

    suspend operator fun invoke(): LaunchListQuery.Launches? {
        val response = apolloClient.query(LaunchListQuery(Optional.present(cursor))).execute()
        return response.data?.launches
    }
}
