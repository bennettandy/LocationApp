package uk.co.avsoftware.locationpresentation.di

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.core.annotation.ApplicationId
import uk.co.avsoftware.location.interactor.GetLastLocationInteractor
import uk.co.avsoftware.location.interactor.IsGPSEnabledInteractor
import uk.co.avsoftware.location.interactor.LocationPermissionEnabledInteractor
import uk.co.avsoftware.locationdomain.interactor.GetCurrentLocationInteractor
import uk.co.avsoftware.locationdomain.interactor.GetGrantedPermissionsInteractor
import uk.co.avsoftware.locationdomain.repository.AndroidLocationEventsRepository

@Module
@InstallIn(SingletonComponent::class)
internal class LocationPermissionsModule {

    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager =
        context.packageManager

    @Provides
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides
    fun provideIsGpsEnabledInteractor(locationManager: LocationManager) = IsGPSEnabledInteractor(locationManager)

    @Provides
    fun provideLocationPermissionEnabledInteractor(permissionsGrantedInteractor: GetGrantedPermissionsInteractor, @ApplicationId appId: String) = LocationPermissionEnabledInteractor(
        permissionsGrantedInteractor,
        appId
    )

    @Provides
    fun getPermissionsGrantedInteractor(packageManager: PackageManager) = GetGrantedPermissionsInteractor(packageManager)

    @Provides
    fun provideGetLastLocationInteractor(locationManager: LocationManager) = GetLastLocationInteractor(locationManager)

    @Provides
    fun provideGetCurrentLocationInteractor(locationEventsRepository: AndroidLocationEventsRepository) = GetCurrentLocationInteractor(locationEventsRepository)
}
