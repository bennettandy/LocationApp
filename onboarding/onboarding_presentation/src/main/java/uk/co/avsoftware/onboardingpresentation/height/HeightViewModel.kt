package uk.co.avsoftware.onboardingpresentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uk.co.avsoftware.common.domain.preferences.Preferences
import uk.co.avsoftware.common.domain.usecase.FilterOutDigits
import uk.co.avsoftware.common.navigation.Route
import uk.co.avsoftware.common.util.UiEvent
import uk.co.avsoftware.common.util.UiText
import uk.co.avsoftware.onboardingpresentation.R
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
) : ViewModel() {
    var height by mutableStateOf("180")
        private set

    // channel for one off UI events
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length <= 3) {
            // filter any non numeric characters
            this.height = filterOutDigits(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = height.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbarEvent(
                        UiText.StringResource(R.string.error_height_cannot_be_empty),
                    ),
                )
                return@launch // skip setting preference for error case
            }
            preferences.saveHeight(heightNumber)
            _uiEvent.send(UiEvent.Navigate(route = Route.WEIGHT))
        }
    }
}
