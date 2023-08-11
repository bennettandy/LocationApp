package uk.co.avsoftware.locationdata.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.locationdata.dao.LocationDao
import uk.co.avsoftware.locationdata.database.LocationDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationDataModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(@ApplicationContext applicationContext: Context): LocationDatabase =
        Room.databaseBuilder(
            applicationContext,
            LocationDatabase::class.java,
            "location-database"
        ).build()

    @Provides
    @Singleton
    fun provideLocationDao(locationDatabase: LocationDatabase): LocationDao = locationDatabase.locationDao
}
