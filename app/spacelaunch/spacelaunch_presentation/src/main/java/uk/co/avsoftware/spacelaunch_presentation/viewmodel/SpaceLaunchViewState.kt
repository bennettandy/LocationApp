package uk.co.avsoftware.spacelaunch_presentation.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uk.co.avsoftware.core.mvi.MviState
import uk.co.avsoftware.spacelaunch_domain.model.Launches

@Parcelize
data class SpaceLaunchViewState(
    val isLoading: Boolean,
    val launches: Launches
) : MviState, Parcelable {

    companion object {
        val default = SpaceLaunchViewState(
            isLoading = false,
            launches = Launches(null, emptyList(), false)
        )
    }
}
