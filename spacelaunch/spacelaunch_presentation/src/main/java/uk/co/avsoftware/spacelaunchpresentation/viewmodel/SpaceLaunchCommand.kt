package uk.co.avsoftware.spacelaunchpresentation.viewmodel

import uk.co.avsoftware.common.mvi.MviCommand

interface SpaceLaunchCommand : MviCommand {
    data class Login(val email: String?) : SpaceLaunchCommand
    data class LoadLaunchList(val cursor: String?) : SpaceLaunchCommand
    data class LoadLaunchDetails(val launchId: String) : SpaceLaunchCommand
    data class BookLaunch(val launchId: String) : SpaceLaunchCommand
    data class CancelLaunch(val launchId: String) : SpaceLaunchCommand
}
