package uk.co.avsoftware.locationpresentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import uk.co.avsoftware.location_presentation.R
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState

@Composable
fun LocationEventDisplay(state: State<LocationPermissionViewState>) {
    Text(
        text = stringResource(R.string.location_event_title)
    )
}