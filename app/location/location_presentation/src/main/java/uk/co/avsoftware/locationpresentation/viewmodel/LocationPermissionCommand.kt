package uk.co.avsoftware.locationpresentation.viewmodel

import uk.co.avsoftware.core.mvi.MviCommand

interface LocationPermissionCommand : MviCommand {
    object RefreshCurrentPermissions : LocationPermissionCommand
    object CheckPermissionsCommand : LocationPermissionCommand
    data class HandleDeniedPermissions(val permissions: Map<String, Boolean>) :
        LocationPermissionCommand

    data class ToggleLocationEventListener(val listening: Boolean) : LocationPermissionCommand
}
