package uk.co.avsoftware.locationapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uk.co.avsoftware.location_presentation.components.LocationPermissionStatusBar
import uk.co.avsoftware.location_presentation.viewmodel.LocationPermissionAction
import uk.co.avsoftware.location_presentation.viewmodel.LocationPermissionEvent
import uk.co.avsoftware.location_presentation.viewmodel.LocationPermissionViewModel
import uk.co.avsoftware.location_presentation.viewmodel.LocationPermissionViewState
import uk.co.avsoftware.locationapp.ui.theme.LocationAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationViewModel: LocationPermissionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onSurface, shape = RectangleShape)
                ) {
                    ScaffoldExample(locationViewModel)
                }
            }
        }

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            locationViewModel.receiveAction(LocationPermissionAction.ProcessPermissionResponse(permissions))
        }

        lifecycleScope.launch {
            locationViewModel.viewEvents.collect() { event ->
                when (event) {
                    is LocationPermissionEvent.ObtainPermissions -> locationPermissionRequest.launch(event.permissions.toTypedArray())
                    // when permission is denied we have to send the user to settings via dialog
                    is LocationPermissionEvent.CoarsePermissionDenied -> showLocationNavigationDialog(
                        getString(R.string.location_spike_location_required_dialog_title)
                    )
                    is LocationPermissionEvent.FinePermissionDenied -> showLocationNavigationDialog(
                        getString(R.string.location_spike_fine_location_required_dialog_title)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // trigger viewmodel refresh
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
            }
        )

    private fun navigateToLocationPermissions() = startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            data = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        }
    )

    @Composable
    fun TopBar(onMenuClicked: () -> Unit) {
        // TopAppBar Composable
        TopAppBar(
            // Provide Title
            title = {
                Text(text = "Scaffold||GFG", color = Color.White)
            },
            // Provide the navigation Icon (Icon on the left to toggle drawer)
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",

                    // When clicked trigger onClick
                    // Callback to trigger drawer open
                    modifier = Modifier.clickable(onClick = onMenuClicked),
                    tint = Color.White
                )
            },
            // background color of topAppBar
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )
    }

    @Composable
    fun BottomBar() {
        // BottomAppBar Composable
        BottomAppBar(
            containerColor = BottomAppBarDefaults.containerColor
        ) {
            Text(text = "Bottom App Bar", color = Color.White)
        }
    }

    @Composable
    fun Drawer() {
        // Column Composable
        Column(
            Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            // Repeat is a loop which
            // takes count as argument
            repeat(5) { item ->
                Text(
                    text = "Item number $item",
                    modifier = Modifier.padding(8.dp),
                    color = Color.Black
                )
            }
        }
    }

    @Composable
    fun Body(state: State<LocationPermissionViewState>) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Body Content, ${state.value}",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ScaffoldExample(viewModel: LocationPermissionViewModel) {
        val state = viewModel.uiState.collectAsState()

        val snackbarHostState = remember { SnackbarHostState() }
        // Create a coroutine scope. Opening of
        // Drawer and snackbar should happen in
        // background thread without blocking main thread
        val coroutineScope = rememberCoroutineScope()

        // Scaffold Composable
        Scaffold(

            // pass the scaffold state
            // scaffoldState = scaffoldState,

            // pass the topbar we created
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
                    }
                )
//                TopBar(
//                    // When menu is clicked open the
//                    // drawer in coroutine scope
//                    onMenuClicked = {
// //                        coroutineScope.launch {
// //                            // to close use -> scaffoldState.drawerState.close()
// //                            scaffoldState.drawerState.open()
// //                        }
//                    })
            },

            // pass the bottomBar
            // we created
            bottomBar = { BottomBar() },

            // Pass the body in
            // content parameter
            content = {
                Body(state)
            },

            // pass the drawer
            // no drawer - see ModalNavigationDrawer
//            drawerContent = {
//                Drawer()
//            },

            floatingActionButton = {
                // Create a floating action button in
                // floatingActionButton parameter of scaffold
                FloatingActionButton(

                    onClick = {
                        // When clicked open Snackbar
                        coroutineScope.launch {
                            when (
                                snackbarHostState.showSnackbar(
                                    // Message In the snackbar
                                    message = "Snack Bar",
                                    actionLabel = "Dismiss"
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
                ) {
                    // Simple Text inside FAB
                    Text(text = "X")
                }
            }
        )
    }
}
