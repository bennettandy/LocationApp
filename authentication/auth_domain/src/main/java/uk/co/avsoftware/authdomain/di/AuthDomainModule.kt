package uk.co.avsoftware.authdomain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.avsoftware.authdomain.repository.AuthenticationRepository
import uk.co.avsoftware.authdomain.repository.impl.AuthenticationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
class AuthDomainModule {
    @Provides
    internal fun provideAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository = authenticationRepositoryImpl
}
