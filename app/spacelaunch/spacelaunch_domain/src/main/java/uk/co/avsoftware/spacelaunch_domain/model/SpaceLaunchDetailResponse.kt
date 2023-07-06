package uk.co.avsoftware.spacelaunch_domain.model

sealed interface SpaceLaunchDetailResponse {
    data class Success(val launchDetails: LaunchDetails?): SpaceLaunchDetailResponse
    data class DomainError(val errorMessage: String?): SpaceLaunchDetailResponse
}