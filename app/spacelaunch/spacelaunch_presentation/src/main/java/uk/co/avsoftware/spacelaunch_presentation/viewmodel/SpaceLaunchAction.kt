package uk.co.avsoftware.spacelaunch_presentation.viewmodel

import uk.co.avsoftware.core.mvi.MviAction

sealed interface SpaceLaunchAction : MviAction {
    object Initialise : SpaceLaunchAction
}
