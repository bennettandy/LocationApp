package uk.co.avsoftware.locationapp.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.common.annotation.ApplicationId
import uk.co.avsoftware.common.data.DefaultPreferences
import uk.co.avsoftware.common.domain.preferences.Preferences
import uk.co.avsoftware.common.domain.usecase.FilterOutDigits
import uk.co.avsoftware.locationapp.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @ApplicationId
    @Singleton
    fun provideApplicationId(): String = BuildConfig.APPLICATION_ID

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences = DefaultPreferences(sharedPreferences)

    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    @Provides
    @Singleton
    fun provideFilterOutDigitsUseCase(): FilterOutDigits = FilterOutDigits()
}
