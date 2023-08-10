package uk.co.avsoftware.location.interactor

import uk.co.avsoftware.locationdomain.interactor.GetGrantedPermissionsInteractor

class LocationPermissionEnabledInteractor(
    private val permissionsGrantedInteractor: GetGrantedPermissionsInteractor,
    private val applicationId: String
) {
    fun coarseLocationEnabled(): Boolean =
        checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)

    fun fineLocationEnabled(): Boolean =
        checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)

    private fun checkPermission(permissionString: String): Boolean =
        permissionsGrantedInteractor(applicationId)[permissionString] ?: false
}
