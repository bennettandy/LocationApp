package uk.co.avsoftware.spacelaunchdomain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.spacelaunchdomain.apollo.ApolloBookedTripsRepository
import uk.co.avsoftware.spacelaunchdomain.apollo.BookLaunchInteractorApollo
import uk.co.avsoftware.spacelaunchdomain.apollo.CancelLaunchBookingInteractorApollo
import uk.co.avsoftware.spacelaunchdomain.apollo.GetLaunchDetailsInteractorApollo
import uk.co.avsoftware.spacelaunchdomain.apollo.GetLaunchListInteractorApollo
import uk.co.avsoftware.spacelaunchdomain.apollo.SpaceLaunchLoginInteractorApollo
import uk.co.avsoftware.spacelaunchdomain.interactor.BookLaunchInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.CancelLaunchBookingInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchDetailsInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.SpaceLaunchLoginInteractor
import uk.co.avsoftware.spacelaunchdomain.repository.BookedTripsRepository

@Module
@InstallIn(SingletonComponent::class)
class SpaceLaunchInteractorModule {

    @Provides
    internal fun provideBookedTripsRepository(apolloBookedTripsRepository: ApolloBookedTripsRepository): BookedTripsRepository = apolloBookedTripsRepository

    @Provides
    internal fun provideBookLaunchInteractor(bookLaunchInteractorApollo: BookLaunchInteractorApollo): BookLaunchInteractor = bookLaunchInteractorApollo

    @Provides
    internal fun provideCancelLaunchBookingInteractor(cancelLaunchBookingInteractorApollo: CancelLaunchBookingInteractorApollo): CancelLaunchBookingInteractor = cancelLaunchBookingInteractorApollo

    @Provides
    internal fun provideGetLaunchDetailsInteractor(getLaunchDetailsInteractorApollo: GetLaunchDetailsInteractorApollo): GetLaunchDetailsInteractor = getLaunchDetailsInteractorApollo

    @Provides
    internal fun provideGetLaunchListInteractor(getLaunchListInteractorApollo: GetLaunchListInteractorApollo): GetLaunchListInteractor = getLaunchListInteractorApollo

    @Provides
    internal fun provideSpaceLaunchLoginInteractor(spaceLaunchLoginInteractorApollo: SpaceLaunchLoginInteractorApollo): SpaceLaunchLoginInteractor = spaceLaunchLoginInteractorApollo
}
