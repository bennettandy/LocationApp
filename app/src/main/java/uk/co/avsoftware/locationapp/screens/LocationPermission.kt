package uk.co.avsoftware.locationapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.co.avsoftware.locationpresentation.components.LocationEventDisplay
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState

@Composable
fun Body(state: State<LocationPermissionViewState>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LocationEventDisplay(state)
    }
}