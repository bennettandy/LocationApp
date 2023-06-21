package uk.co.avsoftware.locationpresentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.avsoftware.location_presentation.R
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState

@Composable
fun LocationEventDisplay(
    state: LocationPermissionViewState,
    locationToggled: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
    Row(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
        Text(
            text = stringResource(R.string.location_event_title),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(15.dp)
        )
    }
    Row() {
        val checkedState = state.locationToggleState
        Switch(
            checked = checkedState,
            onCheckedChange = { locationToggled(!checkedState) }
        )
    }

}

@Preview
@Composable
fun Preview() {
    LocationEventDisplay(state = LocationPermissionViewState.default, locationToggled = {})
}