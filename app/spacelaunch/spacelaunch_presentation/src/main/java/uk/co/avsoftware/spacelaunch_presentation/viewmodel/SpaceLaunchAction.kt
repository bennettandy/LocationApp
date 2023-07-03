package uk.co.avsoftware.spacelaunch_presentation.viewmodel

import uk.co.avsoftware.core.mvi.MviAction
import uk.co.avsoftware.spacelaunch_domain.model.Launches

sealed interface SpaceLaunchAction : MviAction {
    object Initialise : SpaceLaunchAction

    data class RefreshLaunches(val cursor: String?) : SpaceLaunchAction
    data class HandleLaunches(val launches: Launches) : SpaceLaunchAction
}
