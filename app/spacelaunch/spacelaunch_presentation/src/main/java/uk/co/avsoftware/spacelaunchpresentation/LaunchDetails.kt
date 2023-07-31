package uk.co.avsoftware.spacelaunchpresentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uk.co.avsoftware.spacelaunchdomain.model.LaunchDetails
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchAction
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewModel
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewState

@Composable
fun LaunchDetails(
    spaceLaunchViewModel: SpaceLaunchViewModel,
    viewState: SpaceLaunchViewState,
    launchId: String,
) {
    LaunchedEffect(Unit) {
        // load details
        spaceLaunchViewModel.receiveAction(SpaceLaunchAction.LoadDetails(launchId))
    }

    val isLoading = viewState.launchDetailsLoading
    val launchDetails = viewState.launchDetails

    when {
        isLoading -> Loading()
        else -> LaunchDetails(spaceLaunchViewModel, viewState, launchDetails)
    }
}

@Composable
private fun LaunchDetails(
    viewModel: SpaceLaunchViewModel,
    spaceLaunchViewState: SpaceLaunchViewState,
    launchDetails: LaunchDetails?,
) {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Mission patch
            AsyncImage(
                modifier = Modifier.size(160.dp, 160.dp),
                model = launchDetails?.mission?.missionPatch,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = "Mission patch",
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                // Mission name
                Text(
                    style = MaterialTheme.typography.headlineMedium,
                    text = launchDetails?.mission?.name ?: "",
                )

                // Rocket name
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    text = launchDetails?.rocket?.name?.let { "🚀 $it" } ?: "",
                )

                // Site
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = launchDetails?.site ?: "",
                )
            }
        }
        // Book button
        Button(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            enabled = !spaceLaunchViewState.bookingInProgress,
            onClick = {
                launchDetails?.id?.let {
                    if (spaceLaunchViewState.launchDetails?.isBooked == true) {
                        viewModel.receiveAction(SpaceLaunchAction.CancelLaunch(it))
                    } else {
                        viewModel.receiveAction(SpaceLaunchAction.BookLaunch(it))
                    }
                }
            },
        ) {
            if (spaceLaunchViewState.bookingInProgress) {
                SmallLoading()
            } else {
                Text(text = if (spaceLaunchViewState.launchDetails?.isBooked == true) "Cancel Booking" else "Book now")
            }
        }
    }
}

@Composable
// TODO: implement booking error display
private fun ErrorMessage(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SmallLoading() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = LocalContentColor.current,
        strokeWidth = 2.dp,
    )
}

@Preview(showBackground = true)
@Composable
private fun LaunchDetailsPreview() {
    // LaunchDetailslaunchId = "42", navigateToLogin = {})
}
