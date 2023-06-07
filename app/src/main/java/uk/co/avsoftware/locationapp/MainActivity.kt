package uk.co.avsoftware.locationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import uk.co.avsoftware.location_presentation.viewmodel.LocationPermissionAction
import uk.co.avsoftware.location_presentation.viewmodel.LocationPermissionViewModel
import uk.co.avsoftware.locationapp.ui.theme.LocationAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationViewModel: LocationPermissionViewModel by viewModels()

        setContent {
            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onSurface, shape = RectangleShape)
                ) {
                    val state = locationViewModel.uiState.collectAsState()

                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        content = { padding ->
                            Text(modifier = Modifier
                                .padding(padding),
                            text = "hello GPS: ${state.value.gpsIsActive}")
                            }
                    )
                }
            }
        }

        // trigger viewmodel
        locationViewModel.receiveAction(LocationPermissionAction.RefreshCurrentPermissions)

    }
}