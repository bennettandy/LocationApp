package uk.co.avsoftware.locationpresentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uk.co.avsoftware.location_presentation.R
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState

@Composable
fun LocationPermissionStatusBar(
    viewState: LocationPermissionViewState,
    modifier: Modifier = Modifier,
    permissionsClicked: () -> Unit = {},
    gpsSettingsClicked: () -> Unit = {},
    navigateToPermissionsClicked: () -> Unit = {}
) {
    @Composable
    fun getStateString(viewState: LocationPermissionViewState) =
        stringResource(
        when {
            !viewState.coarseLocationGranted -> R.string.location_permission_status_coarse_required
            !viewState.fineLocationGranted -> R.string.location_permission_status_fine_required
            !viewState.gpsIsActive -> R.string.location_permission_status_gps_required

            else -> R.string.location_permission_status_success
        })


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocationStatusChipRow(viewState,
            modifier,
            permissionsClicked,
            gpsSettingsClicked,
            navigateToPermissionsClicked
        )
        
        Row(
            Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = getStateString(viewState), color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationStatusChipRow(
    viewState: LocationPermissionViewState,
    modifier: Modifier = Modifier,
    permissionsClicked: () -> Unit = {},
    gpsSettingsClicked: () -> Unit = {},
    navigateToPermissionsClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FilterChip(selected = viewState.coarseLocationGranted,
            onClick = permissionsClicked,
            trailingIcon = {
                GetStateIcon(successState = viewState.coarseLocationGranted)
            },
            label = {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(id = R.string.location_chip_coarse)
                )
            })

        FilterChip(
            selected = viewState.fineLocationGranted,
            onClick = permissionsClicked,
            trailingIcon = {
                GetStateIcon(successState = viewState.fineLocationGranted)
            },
            label = {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(id = R.string.location_chip_fine)
                )
            }
        )

        FilterChip(
            selected = viewState.gpsIsActive,
            onClick = gpsSettingsClicked,
            trailingIcon = {
                GetStateIcon(successState = viewState.gpsIsActive)
            },
            label = {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(id = R.string.location_chip_gps)
                )
            })

        TextButton(
            onClick = navigateToPermissionsClicked,
            shape = CircleShape,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(id = R.string.location_chip_debug)
            )
        }
    }
}

@Composable
private fun GetStateIcon(successState: Boolean) {
    if (successState) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.baseline_cancel_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
    }
}
