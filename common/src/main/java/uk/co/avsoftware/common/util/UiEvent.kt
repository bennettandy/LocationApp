package uk.co.avsoftware.common.util

sealed interface UiEvent {
    data class Navigate(val route: String) : UiEvent
    object NavigateUp : UiEvent
    data class ShowSnackbarEvent(val uiString: UiText) : UiEvent
}
