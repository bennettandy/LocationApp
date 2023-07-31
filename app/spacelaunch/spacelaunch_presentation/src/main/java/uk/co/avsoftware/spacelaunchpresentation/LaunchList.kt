@file:OptIn(ExperimentalMaterial3Api::class)

package uk.co.avsoftware.spacelaunchpresentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uk.co.avsoftware.spacelaunchdomain.model.Launch
import uk.co.avsoftware.spacelaunchdomain.model.Launches
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchAction
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewModel

@Composable
fun LaunchList(
    onLaunchClick: (launchId: String) -> Unit,
    spaceLaunchViewModel: SpaceLaunchViewModel,
) {
    val cursor: String? by remember { mutableStateOf(null) }

    val uiState = spaceLaunchViewModel.uiState.collectAsState()
    LaunchedEffect(cursor) {
        spaceLaunchViewModel.receiveAction(
            SpaceLaunchAction.RefreshLaunches(cursor),
        )
    }

    LazyColumn {
        val launches: Launches = uiState.value.launches
        items(uiState.value.launches.launches) { launch ->
            launch?.let {
                LaunchItem(launch = launch, onClick = onLaunchClick)
            }
        }

        item {
            if (launches.hasMore) {
                LoadingItem()

                spaceLaunchViewModel.receiveAction(
                    SpaceLaunchAction.RefreshLaunches(launches.cursor),
                )
            }
        }
    }
}

@Composable
private fun LaunchItem(launch: Launch, onClick: (launchId: String) -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onClick(launch.id) },

        headlineContent = {
            // Mission name
            Text(text = launch.mission?.name ?: "")
        },
        supportingContent = {
            // Site
            Text(text = launch.site ?: "")
        },
        leadingContent = {
            // Mission patch
            AsyncImage(
                modifier = Modifier.size(68.dp, 68.dp),
                model = launch.mission?.missionPatch,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = "Mission patch",
            )
        },
    )
}

@Composable
private fun LoadingItem() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        CircularProgressIndicator()
    }
}
