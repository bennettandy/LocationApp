package uk.co.avsoftware.location_presentation.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uk.co.avsoftware.core.mvi.MviState

@Parcelize
data class LocationPermissionViewState(
    val coarseLocationGranted: Boolean,
    val fineLocationGranted: Boolean,
    val gpsIsActive: Boolean
) : MviState, Parcelable {

    fun isValid() = coarseLocationGranted && fineLocationGranted && gpsIsActive
    companion object {
        val default = LocationPermissionViewState(
            coarseLocationGranted = false,
            fineLocationGranted = false,
            gpsIsActive = false
        )
    }

    enum class PermissionState {
        NONE, GRANTED, DENIED
    }
}
