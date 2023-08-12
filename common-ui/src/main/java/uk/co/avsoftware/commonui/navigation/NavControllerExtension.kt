package uk.co.avsoftware.commonui.navigation

import androidx.navigation.NavController
import uk.co.avsoftware.common.util.UiEvent

fun NavController.navigate(navEvent: UiEvent.Navigate){
    this.navigate(navEvent.route)
}