package uk.co.avsoftware.locationdomain.interactor

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

class GetGrantedPermissionsInteractor(private val packageManager: PackageManager) {
    operator fun invoke(applicationId: String): Map<String, Boolean> {
        val packageInfo = packageManager.getPackageInfoCompat(
            applicationId,
            PackageManager.GET_PERMISSIONS
        )

        val requestedPermissions: Array<String> = packageInfo.requestedPermissions ?: emptyArray()

        return requestedPermissions.associateWith { permission ->
            packageManager.checkPermission(
                permission,
                applicationId
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
        } else {
            @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
        }
}
