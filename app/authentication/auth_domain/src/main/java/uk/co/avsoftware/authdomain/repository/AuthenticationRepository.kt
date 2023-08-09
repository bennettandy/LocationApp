package uk.co.avsoftware.authdomain.repository

import kotlinx.coroutines.flow.Flow
import uk.co.avsoftware.authdomain.model.AuthenticatedUser

interface AuthenticationRepository {
    fun getAuthenticatedUsers(): Flow<List<AuthenticatedUser>>
}
