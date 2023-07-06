package uk.co.avsoftware.spacelaunch_presentation.viewmodel

import uk.co.avsoftware.core.mvi.MviAction
import uk.co.avsoftware.spacelaunch_domain.model.LaunchDetails
import uk.co.avsoftware.spacelaunch_domain.model.Launches

sealed interface SpaceLaunchAction : MviAction {
    object Initialise : SpaceLaunchAction

    data class SetEmail(val email: String): SpaceLaunchAction
    object Login : SpaceLaunchAction

    data class SetLoggedIn(val loggedIn: Boolean): SpaceLaunchAction
    data class RefreshLaunches(val cursor: String?) : SpaceLaunchAction
    data class HandleLaunches(val launches: Launches) : SpaceLaunchAction

    data class LoadDetails(val launchId: String) : SpaceLaunchAction
    data class HandleLaunchDetail(val launchDetail: LaunchDetails?) : SpaceLaunchAction
    data class BookLaunch(val launchId: String) : SpaceLaunchAction
    data class CancelLaunch(val launchId: String) : SpaceLaunchAction
    data class UpdateBookingState(val isBooked: Boolean) : SpaceLaunchAction
}
