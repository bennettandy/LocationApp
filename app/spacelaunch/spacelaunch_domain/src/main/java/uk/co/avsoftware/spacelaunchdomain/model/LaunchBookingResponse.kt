package uk.co.avsoftware.spacelaunchdomain.model

sealed interface LaunchBookingResponse {
    object NotLoggedIn : LaunchBookingResponse
    object Success : LaunchBookingResponse
    object Error : LaunchBookingResponse
}
