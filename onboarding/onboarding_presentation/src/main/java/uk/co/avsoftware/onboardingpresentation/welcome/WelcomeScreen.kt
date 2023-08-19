package uk.co.avsoftware.onboardingpresentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import uk.co.avsoftware.common.navigation.Route
import uk.co.avsoftware.common.util.UiEvent
import uk.co.avsoftware.commonui.LocalSpacing
import uk.co.avsoftware.onboardingpresentation.R
import uk.co.avsoftware.onboardingpresentation.components.ActionButton

@Composable
fun WelcomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val dimensions = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(dimensions.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.welcomeText),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        ActionButton(
            text = stringResource(R.string.buttonNext),
            onClick = { onNavigate(UiEvent.Navigate(Route.AGE)) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
