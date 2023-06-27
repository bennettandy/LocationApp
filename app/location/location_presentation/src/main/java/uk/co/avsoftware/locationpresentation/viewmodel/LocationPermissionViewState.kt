package uk.co.avsoftware.locationpresentation.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uk.co.avsoftware.core.mvi.MviState
import uk.co.avsoftware.locationdomain.model.LocationEvent

@Parcelize
data class LocationPermissionViewState(
    val coarseLocationGranted: Boolean,
    val fineLocationGranted: Boolean,
    val gpsIsActive: Boolean,
    val locationToggleState: Boolean,
    val locationEvents: List<LocationEvent>
) : MviState, Parcelable {

    fun permissionsGranted() = coarseLocationGranted && fineLocationGranted && gpsIsActive

    companion object {
        val default = LocationPermissionViewState(
            coarseLocationGranted = false,
            fineLocationGranted = false,
            gpsIsActive = false,
            locationToggleState = false,
            locationEvents = emptyList()
        )
    }
}
