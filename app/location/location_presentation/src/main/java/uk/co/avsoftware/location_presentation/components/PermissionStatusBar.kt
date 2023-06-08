package uk.co.avsoftware.location_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.avsoftware.location_presentation.viewmodel.LocationPermissionViewState

@Composable
fun LocationPermissionStatusBar(
    viewState: LocationPermissionViewState,
    modifier: Modifier = Modifier,
    permissionsClicked: () -> Unit = {},
    gpsSettingsClicked: () -> Unit = {},
    ){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocationStatusChipRow(viewState, modifier)
        LocationPermissionButtonRow(viewState, modifier, permissionsClicked, gpsSettingsClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationStatusChipRow(viewState: LocationPermissionViewState, modifier: Modifier = Modifier){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputChip(selected = viewState.coarseLocationGranted, onClick = { /*TODO*/ }, label = {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "Coarse Location"
            )
        })

        ElevatedFilterChip(
            selected = viewState.fineLocationGranted,
            onClick = { /*TODO*/ },
            label = {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = "Fine Location"
                )
            })

        ElevatedFilterChip(selected = viewState.gpsIsActive, onClick = { /*TODO*/ }, label = {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "GPS Active"
            )
        })
    }
}

@Composable
private fun LocationPermissionButtonRow(
    viewState: LocationPermissionViewState,
    modifier: Modifier = Modifier,
    permissionsClicked: () -> Unit,
    gpsSettingsClicked: () -> Unit
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        OutlinedButton(onClick = permissionsClicked) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "Request Permissions"
            )
        }
        OutlinedButton(onClick = gpsSettingsClicked) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "GPS Settings"
            )
        }
    }
}