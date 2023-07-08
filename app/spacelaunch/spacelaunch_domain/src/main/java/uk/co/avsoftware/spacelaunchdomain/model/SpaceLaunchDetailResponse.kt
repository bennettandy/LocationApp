package uk.co.avsoftware.spacelaunchdomain.model

sealed interface SpaceLaunchDetailResponse {
    data class Success(val launchDetails: LaunchDetails?) : SpaceLaunchDetailResponse
    data class DomainError(val errorMessage: String?) : SpaceLaunchDetailResponse
}
