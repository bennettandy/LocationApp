package uk.co.avsoftware.spacelaunchdata.interactor

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import uk.co.avsoftware.spacelaunchdata.LoginMutation
import uk.co.avsoftware.spacelaunchdata.TokenRepository
import javax.inject.Inject

class ApolloLoginInteractor @Inject constructor(
    private val apolloClient: ApolloClient,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(email: String): Boolean = login(email)

    private suspend fun login(email: String): Boolean {
        val response = try {
            apolloClient.mutation(LoginMutation(email = email)).execute()
        } catch (e: ApolloException) {
            Log.w("Login", "Failed to login", e)
            return false
        }
        if (response.hasErrors()) {
            Log.w("Login", "Failed to login: ${response.errors?.get(0)?.message}")
            return false
        }
        val token = response.data?.login?.token
        if (token == null) {
            Log.w("Login", "Failed to login: no token returned by the backend")
            return false
        }
        tokenRepository.setToken(token)
        return true
    }
}
