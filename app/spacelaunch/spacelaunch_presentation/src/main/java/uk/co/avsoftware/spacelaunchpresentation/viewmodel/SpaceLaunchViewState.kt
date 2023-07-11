package uk.co.avsoftware.spacelaunchpresentation.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uk.co.avsoftware.core.mvi.MviState
import uk.co.avsoftware.spacelaunchdomain.model.LaunchDetails
import uk.co.avsoftware.spacelaunchdomain.model.Launches

@Parcelize
data class SpaceLaunchViewState(
    val email: String?,
    val isLoading: Boolean,
    val isLoggedIn: Boolean,
    val launches: Launches,
    val launchDetails: LaunchDetails?,
    val launchDetailsLoading: Boolean,
    val bookingInProgress: Boolean
) : MviState, Parcelable {

    companion object {
        val default = SpaceLaunchViewState(
            email = null,
            isLoading = false,
            isLoggedIn = false,
            launches = Launches(null, emptyList(), false),
            launchDetails = null,
            launchDetailsLoading = false,
            bookingInProgress = false
        )
    }
}
