package uk.co.avsoftware.spacelaunch_domain.model

sealed interface LaunchBookingResponse {
        object NotLoggedIn : LaunchBookingResponse
        object Success : LaunchBookingResponse
        object Error : LaunchBookingResponse
    }