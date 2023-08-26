package uk.co.avsoftware.authpresentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.co.avsoftware.common.domain.preferences.Preferences
import uk.co.avsoftware.common.navigation.Route
import uk.co.avsoftware.commonui.navigation.navigate
import uk.co.avsoftware.commonui.theme.LocationAppTheme
import uk.co.avsoftware.onboardingpresentation.age.AgeScreen
import uk.co.avsoftware.onboardingpresentation.gender.GenderScreen
import uk.co.avsoftware.onboardingpresentation.height.HeightScreen
import uk.co.avsoftware.onboardingpresentation.weight.WeightScreen
import uk.co.avsoftware.onboardingpresentation.welcome.WelcomeScreen
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AuthenticationFragment : Fragment() {

    @Inject
    lateinit var preferences: Preferences

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                LocationAppTheme {
                    // create Nav Controller instance
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

                    // Scaffold to allow snackbar
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                    ) { padding ->
                        // Navigation Host
                        NavHost(navController = navController, startDestination = Route.WELCOME) {
                            composable(Route.WELCOME) {
                                WelcomeScreen(
                                    navController::navigate,
                                )
                            }
                            composable(Route.AGE) {
                                AgeScreen(
                                    onNavigate = navController::navigate,
                                    snackbarHostState = snackbarHostState,
                                )
                            }
                            composable(Route.GENDER) {
                                GenderScreen(onNavigate = navController::navigate)
                            }
                            composable(Route.HEIGHT) {
                                HeightScreen(
                                    onNavigate = navController::navigate,
                                    snackbarHostState = snackbarHostState,
                                )
                            }
                            composable(Route.WEIGHT) {
                                WeightScreen(
                                    onNavigate = navController::navigate,
                                    snackbarHostState = snackbarHostState,
                                )
                            }
                            composable(Route.NUTRIENT_GOAL) {
                            }
                            composable(Route.ACTIVITY) {
                            }
                            composable(Route.GOAL) {
                            }
                            composable(Route.TRACKER_OVERVIEW) {
                            }
                            composable(Route.SEARCH) {
                            }
                        }
                    }
                }
            }
        }
    }
}
