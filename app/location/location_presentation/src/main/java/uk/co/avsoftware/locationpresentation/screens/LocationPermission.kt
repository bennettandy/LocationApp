package uk.co.avsoftware.locationpresentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.co.avsoftware.locationpresentation.components.CachedEventList
import uk.co.avsoftware.locationpresentation.components.LocationEventDisplay
import uk.co.avsoftware.locationpresentation.viewmodel.LocationPermissionViewState

@Composable
fun Body(
    state: LocationPermissionViewState,
    locationToggled: (Boolean) -> Unit,
    clearLocationList: () -> Unit,
    modifier: Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row {
            LocationEventDisplay(state, locationToggled = locationToggled)
        }
        Row {
            CachedEventList(state.locationEvents, clearLocationList)
        }
    }
}
