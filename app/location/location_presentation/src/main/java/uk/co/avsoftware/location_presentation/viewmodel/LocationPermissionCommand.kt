package uk.co.avsoftware.location_presentation.viewmodel

import uk.co.avsoftware.core.mvi.MviCommand


interface LocationPermissionCommand : MviCommand {
    object RefreshCurrentPermissions : LocationPermissionCommand
    object CheckPermissionsCommand : LocationPermissionCommand
    data class HandleDeniedPermissions(val permissions: Map<String, Boolean>) :
        LocationPermissionCommand
}
