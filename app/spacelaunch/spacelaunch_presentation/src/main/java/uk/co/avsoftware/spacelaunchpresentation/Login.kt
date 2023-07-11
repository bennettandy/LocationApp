@file:OptIn(ExperimentalMaterial3Api::class)

package uk.co.avsoftware.spacelaunchpresentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import timber.log.Timber
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchAction
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewModel
import uk.co.avsoftware.spacelaunchpresentation.viewmodel.SpaceLaunchViewState

@Composable
fun Login(
    spaceLaunchViewModel: SpaceLaunchViewModel,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val viewState: State<SpaceLaunchViewState> = spaceLaunchViewModel.uiState.collectAsState()

        // Title
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            text = "Login"
        )

        val scope = rememberCoroutineScope()

        LaunchedEffect(viewState.value) {
            Timber.d("Check Logged in state: ${viewState.value.isLoggedIn}")
            if (viewState.value.isLoggedIn) {
                scope.launch {
                    navigateBack()
                }
            }
        }

        // Email
        // var email by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = viewState.value.email.orEmpty(),
            onValueChange = {
                scope.launch {
                    spaceLaunchViewModel.receiveAction(SpaceLaunchAction.SetEmail(it))
                }
            }
        )

        // Submit button
        // var loading by remember { mutableStateOf(false) }

        Button(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            enabled = !viewState.value.isLoading,
            onClick = {
                scope.launch {
                    if (viewState.value.isLoggedIn) {
                        navigateBack()
                    } else {
                        // spaceLaunchViewModel.receiveAction(SpaceLaunchAction.ShowLoading)
                        // loading = true
                        spaceLaunchViewModel.receiveAction(SpaceLaunchAction.Login)
//                    val ok = login(tokenRepository, apolloClient, email)
//                    loading = false
//                    if (ok) navigateBack()
                    }
                }
            }
        ) {
            if (viewState.value.isLoading) {
                Loading()
            } else {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
private fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = LocalContentColor.current,
        strokeWidth = 2.dp
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    // Login(navigateBack = { })
}
