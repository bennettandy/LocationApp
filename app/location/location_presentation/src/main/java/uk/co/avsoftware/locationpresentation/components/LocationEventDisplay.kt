package uk.co.avsoftware.locationpresentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.avsoftware.locationpresentation.R
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState

@Composable
fun LocationEventDisplay(
    state: LocationPermissionViewState,
    locationToggled: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
    }
    Row {
        LocationEventsControlSwitch(
            enabled = state.permissionsGranted(),
            active = state.locationToggleState,
            setActive = locationToggled,
        )
    }
    Row(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
        Text(
            text = stringResource(R.string.location_event_title, state.locationEvents.size),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(15.dp),
        )
    }
}

@Preview
@Composable
fun Preview() {
    LocationEventDisplay(state = LocationPermissionViewState.default, locationToggled = {})
}
