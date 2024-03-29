package uk.co.avsoftware.onboardingpresentation.height

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.avsoftware.common.util.UiEvent
import uk.co.avsoftware.commonui.LocalSpacing
import uk.co.avsoftware.commonui.ui.UnitTextField
import uk.co.avsoftware.onboardingpresentation.R
import uk.co.avsoftware.onboardingpresentation.components.ActionButton

@Composable
fun HeightScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HeightViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState, // scope to this graph
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        // why not collect as state?
        viewModel.uiEvent.collect {
                event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbarEvent -> {
                    snackbarHostState.showSnackbar(event.uiString.asString(context))
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_height),
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.height,
                onValueChange = viewModel::onHeightEnter,
                unit = stringResource(id = R.string.centimetres),
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}
