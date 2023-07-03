package uk.co.avsoftware.spacelaunch_presentation.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uk.co.avsoftware.core.mvi.MviState

@Parcelize
data class SpaceLaunchViewState(
    val placeholder: Boolean
) : MviState, Parcelable {

    companion object {
        val default = SpaceLaunchViewState(
            placeholder = false
        )
    }
}
