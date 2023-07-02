package uk.co.avsoftware.core.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.avsoftware.core.extensions.Reducer
import uk.co.avsoftware.core.extensions.getMviState
import uk.co.avsoftware.core.extensions.setMviState

abstract class AbstractMviViewModel<A : MviAction, S : MviState, C : MviCommand>(
    initialState: S,
    private val savedStateHandle: SavedStateHandle,
    private val applicationId: String
) : ViewModel() {

    abstract val reducer: Reducer<A, S, C>

    private val _uiState = MutableStateFlow(savedStateHandle.getMviState(applicationId) ?: initialState)
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()

    fun receiveAction(action: A) {
        reducer(action, uiState.value).apply {
            _uiState.value = first
            savedStateHandle.setMviState(applicationId, first)
            second?.let(::executeCommand)
        }
    }

    protected abstract fun executeCommand(command: C)

    fun S.then(command: C) = Pair(this, command)
    fun S.only() = Pair(this, null)
}
