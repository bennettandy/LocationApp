package uk.co.avsoftware.locationapp.screens

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomBar() {
    // BottomAppBar Composable
    BottomAppBar(
        containerColor = BottomAppBarDefaults.containerColor
    ) {
        Text(text = "Bottom App Bar", color = Color.White)
    }
}