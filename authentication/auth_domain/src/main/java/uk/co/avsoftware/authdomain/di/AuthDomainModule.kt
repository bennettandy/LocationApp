package uk.co.avsoftware.authdomain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.internal.InternalAuthProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDomainModule {
    @Binds
    abstract fun bindsInternalAuthProvider(firebaseAuth: FirebaseAuth): InternalAuthProvider
}
