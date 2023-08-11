package uk.co.avsoftware.authdomain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.authdomain.repository.AuthenticationRepository
import uk.co.avsoftware.authdomain.repository.impl.AuthenticationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDomainModule {
    @Binds
    internal abstract fun provideAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}
