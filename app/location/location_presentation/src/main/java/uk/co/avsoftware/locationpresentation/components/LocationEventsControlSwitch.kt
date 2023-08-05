package uk.co.avsoftware.locationpresentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.co.avsoftware.locationpresentation.R

@Composable
fun LocationEventsControlSwitch(enabled: Boolean, active: Boolean, setActive: (Boolean) -> Unit) {
    Row {
        Text(
            text = stringResource(
                when {
                    !enabled -> R.string.location_grant_permissions
                    active -> R.string.location_listening_label
                    else -> R.string.location_listen_label
                },
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(15.dp),
        )
        Switch(
            checked = active,
            enabled = enabled,
            onCheckedChange = { setActive(!active) },
        )
    }
}
