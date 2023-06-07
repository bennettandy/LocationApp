package uk.co.avsoftware.locationapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.locationapp.BuildConfig
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Named("App ID")
    fun provideApplicationId(): String = BuildConfig.APPLICATION_ID
}