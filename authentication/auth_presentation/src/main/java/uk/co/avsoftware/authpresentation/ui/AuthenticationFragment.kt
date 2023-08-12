package uk.co.avsoftware.authpresentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.co.avsoftware.common.navigation.Route
import uk.co.avsoftware.commonui.navigation.navigate
import uk.co.avsoftware.commonui.theme.LocationAppTheme

/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AuthenticationFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

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

                    // Navigation Host
                    NavHost(navController = navController, startDestination = Route.WELCOME) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(navController::navigate)
                        }
                        composable(Route.AGE) {
                            Column(modifier = Modifier.fillMaxSize()){
                                Text(text = "AGE PLACE HOLDER")
                            }
                        }
                        composable(Route.GENDER) {
                        }
                        composable(Route.HEIGHT) {
                        }
                        composable(Route.WEIGHT) {
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
