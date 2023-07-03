package uk.co.avsoftware.spacelaunch_presentation.viewmodel

import uk.co.avsoftware.core.mvi.MviCommand

interface SpaceLaunchCommand : MviCommand {
    object BookTrip : SpaceLaunchCommand
}
