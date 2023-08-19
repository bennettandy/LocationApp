package uk.co.avsoftware.onboardingpresentation.gender

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
import uk.co.avsoftware.common.domain.model.Gender
import uk.co.avsoftware.common.domain.preferences.Preferences
import uk.co.avsoftware.common.navigation.Route
import uk.co.avsoftware.common.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {
    var selectedGender: Gender by mutableStateOf(Gender.Male)
        private set // can only change value from inside view model

    // channel for one off UI events
    // stateless Flow
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    fun onGenderClick(gender: Gender) {
        selectedGender = gender
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGender(selectedGender)
            _uiEvent.send(UiEvent.Navigate(Route.AGE))
        }
    }
}
