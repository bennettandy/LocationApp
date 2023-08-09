package uk.co.avsoftware.authdata.repository

import kotlinx.coroutines.flow.Flow
import uk.co.avsoftware.authdata.dao.AuthenticationDao
import uk.co.avsoftware.authdata.entities.User
import javax.inject.Inject

class FirebaseAuthenticationRepository @Inject constructor(
    private val authenticationDao: AuthenticationDao,
) {
    fun getAuthenticatedUsers(): Flow<List<User>> = authenticationDao.getAllUsers()
    fun deleteUser(user: User) = authenticationDao.deleteUser(user)
    fun insertUser(user: User) = authenticationDao.insertUser(user)
    fun upsertUser(user: User) = authenticationDao.upsertUser(user)
}
