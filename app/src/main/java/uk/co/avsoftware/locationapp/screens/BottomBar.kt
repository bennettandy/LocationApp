package uk.co.avsoftware.locationapp.screens

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
fun BottomBar() {
    // BottomAppBar Composable
    BottomAppBar(
        containerColor = BottomAppBarDefaults.containerColor,
        modifier = Modifier.testTag("BottomBar")
    ) {
        Text(text = "Bottom App Bar", color = Color.White)
    }
}
