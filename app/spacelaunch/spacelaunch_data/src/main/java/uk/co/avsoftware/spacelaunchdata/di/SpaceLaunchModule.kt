package uk.co.avsoftware.spacelaunchdata.di

import android.content.Context
import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import uk.co.avsoftware.spacelaunchdata.AuthorizationInterceptor
import uk.co.avsoftware.spacelaunchdata.TokenRepository

@Module
@InstallIn(SingletonComponent::class)
class SpaceLaunchModule {

    @Provides
    fun provideTokenRepository(@ApplicationContext context: Context): TokenRepository =
        TokenRepository().apply {
            init(context)
        }

    @Provides
    fun provideAuthorizationInterceptor(tokenRepository: TokenRepository) = AuthorizationInterceptor(tokenRepository)

    @Provides
    fun provideApolloClient(authorizationInterceptor: AuthorizationInterceptor): ApolloClient =
        ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(authorizationInterceptor)
                    .build()
            )
            .webSocketReopenWhen { throwable, attempt ->
                Log.d("Apollo", "WebSocket got disconnected, reopening after a delay", throwable)
                delay(attempt * 1000)
                true
            }
            .build()
}
