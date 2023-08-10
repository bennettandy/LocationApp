package uk.co.avsoftware.authdomain.interactor

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserInteractor @Inject constructor(
    private val authProvider: FirebaseAuth,
) {
    operator fun invoke(): FirebaseUser? = authProvider.currentUser
}
