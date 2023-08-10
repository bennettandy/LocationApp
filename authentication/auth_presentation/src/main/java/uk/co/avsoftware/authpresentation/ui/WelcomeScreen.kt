package uk.co.avsoftware.authpresentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import uk.co.avsoftware.authpresentation.R
import uk.co.avsoftware.authpresentation.ui.components.ActionButton
import uk.co.avsoftware.commonui.LocalSpacing

@Composable
fun WelcomeScreen() {
    val dimensions = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxSize(),
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
            onClick = { /* TODO */ },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
