package uk.co.avsoftware.locationapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import uk.co.avsoftware.commonui.Dimensions
import uk.co.avsoftware.commonui.LocalSpacing
import uk.co.avsoftware.locationapp.screens.Body
import uk.co.avsoftware.locationapp.screens.BottomBar
import uk.co.avsoftware.locationapp.ui.theme.LocationAppTheme
import uk.co.avsoftware.locationpresentation.components.LocationPermissionStatusBar
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionAction
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionEvent
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationViewModel: LocationPermissionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocationAppTheme {
                // our local spacing values from core-uk
                val spacing: Dimensions = LocalSpacing.current
                Timber.d("Spacing values obtained -  medium = ${spacing.spaceMedium}")

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onSurface, shape = RectangleShape),
                ) {
                    ScaffoldExample(locationViewModel)
                }
            }
        }

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions: Map<String, Boolean> ->
            locationViewModel.receiveAction(
                LocationPermissionAction.ProcessPermissionResponse(
                    permissions,
                ),
            )
        }

        lifecycleScope.launch {
            locationViewModel.viewEvents.collect { event ->
                when (event) {
                    is LocationPermissionEvent.ObtainPermissions -> locationPermissionRequest.launch(
                        event.permissions.toTypedArray(),
                    )
                    // when permission is denied we have to send the user to settings via dialog
                    is LocationPermissionEvent.CoarsePermissionDenied -> showLocationNavigationDialog(
                        getString(R.string.location_spike_location_required_dialog_title),
                    )

                    is LocationPermissionEvent.FinePermissionDenied -> showLocationNavigationDialog(
                        getString(R.string.location_spike_fine_location_required_dialog_title),
                    )

                    is LocationPermissionEvent.ListenerStarted -> showToast(getString(R.string.location_toast_listener_started))
                    is LocationPermissionEvent.ListenerStopped -> showToast(getString(R.string.location_toast_listener_stopped))
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        // trigger viewModel refresh
        locationViewModel.receiveAction(LocationPermissionAction.RefreshCurrentPermissions)
    }

    private fun showLocationNavigationDialog(title: String) =
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(R.string.location_fine_location_required_dialog_message)
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                navigateToLocationPermissions()
                    .also { dialog.dismiss() }
            }
            .show()

    private fun navigateToGPSSettings() =
        startActivity(
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
            },
        )

    private fun navigateToLocationPermissions() = startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            data = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        },
    )

    private fun navigateToRocketService() =
        startActivity(
            Intent(this, RocketReserverActivity::class.java),
        )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ScaffoldExample(viewModel: LocationPermissionViewModel) {
        val state = viewModel.uiState.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }
        // Create a coroutine scope. Opening of
        // Drawer and snackbar should happen in
        // background thread without blocking main thread
        val coroutineScope = rememberCoroutineScope()

        val locationToggled: (Boolean) -> Unit = { toggleState ->
            coroutineScope.launch {
                viewModel.receiveAction(LocationPermissionAction.ToggleLocationListening(toggleState))
            }
        }

        // Scaffold Composable
        Scaffold(

            // pass the scaffold state
            // scaffoldState = scaffoldState,

            // pass the topBar we created
            topBar = {
                LocationPermissionStatusBar(
                    state.value,
                    permissionsClicked = {
                        coroutineScope.launch {
                            viewModel.receiveAction(LocationPermissionAction.RequestPermissionClicked)
                        }
                    },
                    gpsSettingsClicked = {
                        coroutineScope.launch {
                            navigateToGPSSettings()
                        }
                    },
                    navigateToPermissionsClicked = {
                        coroutineScope.launch {
                            navigateToLocationPermissions()
                        }
                    },
                )
            },

            // pass the bottomBar
            // we created
            bottomBar = { BottomBar() },

            // Pass the body in
            // content parameter
            content = {
                Body(state.value, locationToggled = locationToggled)
            },

            floatingActionButton = {
                // Create a floating action button in
                // floatingActionButton parameter of scaffold
                FloatingActionButton(
                    modifier = Modifier.testTag("Floating Button"),
                    onClick = {
                        showSnackbar(coroutineScope, snackbarHostState)
                        navigateToRocketService()
                    },
                ) {
                    // Simple Text inside FAB
                    Text(text = "Rockets")
                }
            },
        )
    }

    private fun showSnackbar(coroutineScope: CoroutineScope, snackbarHostState: SnackbarHostState) {
        coroutineScope.launch {
            when (
                snackbarHostState.showSnackbar(
                    // Message In the snackbar
                    message = "Snack Bar",
                    actionLabel = "Dismiss",
                )
            ) {
                SnackbarResult.Dismissed -> {
                    // do something when
                    // snack bar is dismissed
                }

                SnackbarResult.ActionPerformed -> {
                    // when it appears
                }
            }
        }
    }
}
