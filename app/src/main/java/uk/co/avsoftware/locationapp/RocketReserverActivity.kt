package uk.co.avsoftware.locationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apollographql.apollo3.ApolloClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import uk.co.avsoftware.locationapp.ui.theme.LocationAppTheme
import uk.co.avsoftware.spacelaunch_data.TokenRepository
import uk.co.avsoftware.spacelaunch_domain.model.TripBookedResponse
import uk.co.avsoftware.spacelaunch_domain.repository.BookedTripsRepository
import uk.co.avsoftware.spacelaunch_presentation.LaunchDetails
import uk.co.avsoftware.spacelaunch_presentation.LaunchList
import uk.co.avsoftware.spacelaunch_presentation.Login
import javax.inject.Inject

@AndroidEntryPoint
class RocketReserverActivity : ComponentActivity() {

    @Inject
    lateinit var apolloClient: ApolloClient

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    lateinit var bookedTripsRepository: BookedTripsRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocationAppTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                val tripBookedFlow: Flow<TripBookedResponse?> = remember {
                    bookedTripsRepository.bookedTripsFlow()
                        .onStart { Timber.d("Connected Booked Trips Flow") }
                        .onEach { Timber.d("Booked ${it?.tripsBooked} Trips") }
                        .onCompletion { Timber.d("Completed Booked Trips Flow") }
                }
                val tripBookedResponse: TripBookedResponse?  by tripBookedFlow.collectAsState(initial = null)

                LaunchedEffect(tripBookedResponse) {
                    if (tripBookedResponse == null) return@LaunchedEffect
                    val message = when (tripBookedResponse!!.tripsBooked) {
                        null -> "Subscription error"
                        -1 -> "Trip cancelled"
                        else -> "Trip booked! 🚀"
                    }
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }

                Scaffold(
                    topBar = { TopAppBar({ Text(stringResource(R.string.app_name)) }) },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
                        MainNavHost(apolloClient, tokenRepository)
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavHost(apolloClient: ApolloClient, tokenRepository: TokenRepository) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationDestinations.LAUNCH_LIST) {
        composable(route = NavigationDestinations.LAUNCH_LIST) {
            LaunchList(
                apolloClient,
                onLaunchClick = { launchId ->
                    navController.navigate("${NavigationDestinations.LAUNCH_DETAILS}/$launchId")
                }
            )
        }

        composable(route = "${NavigationDestinations.LAUNCH_DETAILS}/{${NavigationArguments.LAUNCH_ID}}") { navBackStackEntry ->
            LaunchDetails(
                tokenRepository,
                apolloClient,
                launchId = navBackStackEntry.arguments!!.getString(NavigationArguments.LAUNCH_ID)!!,
                navigateToLogin = {
                    navController.navigate(NavigationDestinations.LOGIN)
                }
            )
        }

        composable(route = NavigationDestinations.LOGIN) {
            Login(
                tokenRepository,
                apolloClient,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
