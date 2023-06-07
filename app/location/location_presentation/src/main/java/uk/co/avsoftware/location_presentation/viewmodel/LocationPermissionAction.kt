package uk.co.avsoftware.location_presentation.viewmodel

import uk.co.avsoftware.core.mvi.MviAction

internal sealed interface LocationPermissionAction : MviAction {
    object RefreshCurrentPermissions : LocationPermissionAction
    data class UpdateLocationPermissions(val coarse: Boolean, val fine: Boolean, val gpsActivity: Boolean) :
        LocationPermissionAction
    object RequestPermissionClicked : LocationPermissionAction
    data class ProcessPermissionResponse(val permissions: Map<String, Boolean>) :
        LocationPermissionAction
}
