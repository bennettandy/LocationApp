package uk.co.avsoftware.onboardingpresentation.gender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.avsoftware.common.domain.model.Gender
import uk.co.avsoftware.common.util.UiEvent
import uk.co.avsoftware.commonui.LocalSpacing
import uk.co.avsoftware.onboardingpresentation.R
import uk.co.avsoftware.onboardingpresentation.components.ActionButton
import uk.co.avsoftware.onboardingpresentation.components.SelectableButton

@Composable
fun GenderScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: GenderViewModel = hiltViewModel(), // scope to this graph
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = true) {
        // why not collect as state?
        viewModel.uiEvent.collect {
                event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(spacing.spaceLarge)) {
        // can we have column without box?
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(
                modifier = Modifier.height(spacing.spaceMedium),
            )
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.male),
                    isSelected = viewModel.selectedGender is Gender.Male,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGenderClick(Gender.Male)
                    },
                    textStyle = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal,
                    ),
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.female),
                    isSelected = viewModel.selectedGender is Gender.Female,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGenderClick(Gender.Female)
                    },
                    textStyle = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal,
                    ),
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}
