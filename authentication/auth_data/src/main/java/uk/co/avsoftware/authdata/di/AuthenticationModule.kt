package uk.co.avsoftware.authdata.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import database.AuthenticationDatabase
import uk.co.avsoftware.authdata.dao.AuthenticationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideLocationDatabase(@ApplicationContext applicationContext: Context): AuthenticationDatabase =
        Room.databaseBuilder(
            applicationContext,
            AuthenticationDatabase::class.java,
            "auth-database",
        ).build()

    @Provides
    @Singleton
    fun provideLocationDao(authenticationDatabase: AuthenticationDatabase): AuthenticationDao = authenticationDatabase.authenticationDao
}
