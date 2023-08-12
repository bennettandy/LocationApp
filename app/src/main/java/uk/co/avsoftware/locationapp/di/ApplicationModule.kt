package uk.co.avsoftware.locationapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.common.annotation.ApplicationId
import uk.co.avsoftware.locationapp.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @ApplicationId
    @Singleton
    fun provideApplicationId(): String = BuildConfig.APPLICATION_ID
}
