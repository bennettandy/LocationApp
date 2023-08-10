package uk.co.avsoftware.spacelaunchpresentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import uk.co.avsoftware.common.annotation.ApplicationId
import uk.co.avsoftware.common.coroutines.DispatcherProvider
import uk.co.avsoftware.commontest.coroutines.UnitTestDispatcherProvider
import uk.co.avsoftware.spacelaunchdomain.di.SpaceLaunchInteractorModule
import uk.co.avsoftware.spacelaunchdomain.interactor.BookLaunchInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.CancelLaunchBookingInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchDetailsInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.SpaceLaunchLoginInteractor
import uk.co.avsoftware.spacelaunchdomain.model.LaunchBookingResponse
import uk.co.avsoftware.spacelaunchdomain.model.Launches
import uk.co.avsoftware.spacelaunchdomain.model.SpaceLaunchDetailResponse
import uk.co.avsoftware.spacelaunchdomain.model.TripBookedResponse
import uk.co.avsoftware.spacelaunchdomain.repository.BookedTripsRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SpaceLaunchInteractorModule::class]
)
class SpaceLaunchInteractorTestModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = UnitTestDispatcherProvider()

    @Provides
    @ApplicationId
    fun provideTestAppId() = "TestID"

    @Provides
    internal fun provideBookedTripsRepository(): BookedTripsRepository = object : BookedTripsRepository {
        override fun bookedTripsFlow(): Flow<TripBookedResponse?> {
            return flowOf(null)
        }
    }

    @Provides
    internal fun provideBookLaunchInteractor(): BookLaunchInteractor = object : BookLaunchInteractor {
        override suspend fun invoke(launchId: String): LaunchBookingResponse {
            TODO("Not yet implemented")
        }
    }

    @Provides
    internal fun provideCancelLaunchBookingInteractor(): CancelLaunchBookingInteractor = object : CancelLaunchBookingInteractor {
        override suspend fun invoke(launchId: String): LaunchBookingResponse {
            TODO("Not yet implemented")
        }
    }

    @Provides
    internal fun provideGetLaunchDetailsInteractor(): GetLaunchDetailsInteractor = object : GetLaunchDetailsInteractor {
        override suspend fun invoke(launchId: String): SpaceLaunchDetailResponse {
            TODO("Not yet implemented")
        }
    }

    @Provides
    internal fun provideGetLaunchListInteractor(): GetLaunchListInteractor = object : GetLaunchListInteractor {
        override suspend fun invoke(): Launches? {
            TODO("Not yet implemented")
        }
    }

    @Provides
    internal fun provideSpaceLaunchLoginInteractor(): SpaceLaunchLoginInteractor = object : SpaceLaunchLoginInteractor {
        override suspend fun invoke(email: String): Boolean {
            TODO("Not yet implemented")
        }
    }
}
