package uk.co.avsoftware.authdomain.interactor

import uk.co.avsoftware.authdata.interactor.CreateUserInteractor
import javax.inject.Inject

class CreateUserFromEmailAndPassword @Inject constructor(
    private val createUserInteractor: CreateUserInteractor,
) {
    suspend operator fun invoke(email: String, password: String) =
        createUserInteractor.invoke(
            email = email,
            password = password,
        )
}
