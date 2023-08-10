package uk.co.avsoftware.authdomain.interactor

import javax.inject.Inject

class IsUserAuthenticatedInteractor @Inject constructor(
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
) {
    operator fun invoke(): Boolean = getCurrentUserInteractor() != null
}
