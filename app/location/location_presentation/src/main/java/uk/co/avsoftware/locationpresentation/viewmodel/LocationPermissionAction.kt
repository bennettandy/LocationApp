package uk.co.avsoftware.locationpresentation.viewmodel

import uk.co.avsoftware.common.mvi.MviAction
import uk.co.avsoftware.locationdomain.model.LocationEvent

sealed interface LocationPermissionAction : MviAction {
    object RefreshCurrentPermissions : LocationPermissionAction
    data class UpdateLocationPermissions(val coarse: Boolean, val fine: Boolean, val gpsActivity: Boolean) :
        LocationPermissionAction
    object RequestPermissionClicked : LocationPermissionAction
    data class ProcessPermissionResponse(val permissions: Map<String, Boolean>) :
        LocationPermissionAction
    data class ToggleLocationListening(val listening: Boolean) : LocationPermissionAction
    data class ProcessLocationEvent(val event: LocationEvent) : LocationPermissionAction
}
