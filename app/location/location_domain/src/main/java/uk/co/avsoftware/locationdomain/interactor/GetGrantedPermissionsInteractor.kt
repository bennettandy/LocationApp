package uk.co.avsoftware.locationdomain.interactor

import android.content.pm.PackageManager

class GetGrantedPermissionsInteractor(private val packageManager: PackageManager) {
    operator fun invoke(applicationId: String): Map<String, Boolean> {
        val packageInfo = packageManager.getPackageInfo(
            applicationId,
            PackageManager.GET_PERMISSIONS
        )

        val requestedPermissions: Array<String> = packageInfo.requestedPermissions ?: emptyArray()

        return requestedPermissions.associateWith { permission ->
            packageManager.checkPermission(
                permission,
                applicationId
            ) == PackageManager.PERMISSION_GRANTED
        }.also {
            // Arbor.d("MAP $it")
        }
    }
}
