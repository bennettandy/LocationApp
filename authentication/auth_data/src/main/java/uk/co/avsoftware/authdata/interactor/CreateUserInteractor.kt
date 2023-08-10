package uk.co.avsoftware.authdata.interactor

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreateUserInteractor @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    suspend operator fun invoke(email: String, password: String): AuthResult? =
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
}
