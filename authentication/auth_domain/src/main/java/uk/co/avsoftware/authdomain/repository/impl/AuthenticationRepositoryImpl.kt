package uk.co.avsoftware.authdomain.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.avsoftware.authdata.entities.User
import uk.co.avsoftware.authdata.repository.FirebaseAuthenticationRepository
import uk.co.avsoftware.authdomain.model.AuthenticatedUser
import uk.co.avsoftware.authdomain.repository.AuthenticationRepository
import javax.inject.Inject

internal class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) : AuthenticationRepository {

    override fun getAuthenticatedUsers(): Flow<List<AuthenticatedUser>> {
        return firebaseAuthenticationRepository.getAuthenticatedUsers().map {
                userList: List<User> ->
            userList.map {
                AuthenticatedUser(
                    uuid = it.uuid,
                )
            }
        }
    }
}
