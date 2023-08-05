package uk.co.avsoftware.spacelaunchpresentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.co.avsoftware.commonui.theme.LocationAppTheme
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchEvent
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewModel
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewState

@AndroidEntryPoint
class SpaceLaunchFragment : Fragment() {

    private val spaceLaunchViewModel: SpaceLaunchViewModel by activityViewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                LocationAppTheme {
                    val snackbarHostState = remember { SnackbarHostState() }
                    val navController = rememberNavController()
                    val spaceLaunchEvent = spaceLaunchViewModel.viewEvents.collectAsState()
                    val spaceLaunchViewState = spaceLaunchViewModel.uiState.collectAsState()

                    LaunchedEffect(spaceLaunchEvent.value) {
                        if (spaceLaunchEvent.value is SpaceLaunchEvent.NavigateToLogin) {
                            navController.navigate(NavigationDestinations.LOGIN)
                        }

                        val message = when (spaceLaunchEvent.value) {
                            is SpaceLaunchEvent.SubscriptionError -> "Subscription error"
                            is SpaceLaunchEvent.TripCancelled -> "Trip cancelled"
                            is SpaceLaunchEvent.TripBooked -> "Trip booked! 🚀"
                            is SpaceLaunchEvent.LoggedIn -> "Logged in!"
                            is SpaceLaunchEvent.BookingFailed -> "Booking Failed"
                            is SpaceLaunchEvent.CancelFailed -> "Cancellation Failed"
                            else -> return@LaunchedEffect
                        }
                        snackbarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short
                        )
                    }

                    Scaffold(
                        topBar = { TopAppBar({ Text(stringResource(R.string.title_rocket_reservations)) }) },
                        snackbarHost = { SnackbarHost(snackbarHostState) }
                    ) { paddingValues ->
                        Box(Modifier.padding(paddingValues)) {
                            MainNavHost(
                                navController,
                                spaceLaunchViewModel,
                                spaceLaunchViewState.value
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MainNavHost(
        navController: NavHostController,
        viewModel: SpaceLaunchViewModel,
        viewState: SpaceLaunchViewState
    ) {
        NavHost(navController, startDestination = NavigationDestinations.LAUNCH_LIST) {
            composable(route = NavigationDestinations.LAUNCH_LIST) {
                LaunchList(
                    onLaunchClick = { launchId ->
                        navController.navigate("${NavigationDestinations.LAUNCH_DETAILS}/$launchId")
                    },
                    viewModel
                )
            }

            composable(route = "${NavigationDestinations.LAUNCH_DETAILS}/{${NavigationArguments.LAUNCH_ID}}") { navBackStackEntry ->
                LaunchDetails(
                    spaceLaunchViewModel = viewModel,
                    viewState = viewState,
                    launchId = navBackStackEntry.arguments!!.getString(NavigationArguments.LAUNCH_ID)!!
                )
            }

            composable(route = NavigationDestinations.LOGIN) {
                Login(
                    spaceLaunchViewModel = viewModel,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
