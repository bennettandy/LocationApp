package uk.co.avsoftware.locationpresentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.co.avsoftware.commonui.Dimensions
import uk.co.avsoftware.commonui.LocalSpacing
import uk.co.avsoftware.locationdomain.model.LocationEvent
import uk.co.avsoftware.locationpresentation.R

@Composable
fun CachedEventList(state: List<LocationEvent>, clearListClicked: () -> Unit) {
    val spacing: Dimensions = LocalSpacing.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceMedium)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        item {
            Text(text = "Cached Location List")
        }
        // Add 5 items
        items(state.size) { index ->
            Text(text = "Lat: ${state[index].latitude}")
            Text(text = "Lon: ${state[index].longitude}")
        }
        item {
            Button(onClick = clearListClicked) {
                Text(text = "Clear")
            }
        }
        item {
            Text(
                text = stringResource(R.string.location_event_title, state.size),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}
