package uk.co.avsoftware.spacelaunch_presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import uk.co.avsoftware.core.annotation.ApplicationId
import uk.co.avsoftware.core.extensions.Reducer
import uk.co.avsoftware.core.mvi.AbstractMviViewModel
import uk.co.avsoftware.spacelaunch_domain.interactor.GetLaunchListInteractor
import uk.co.avsoftware.spacelaunch_domain.repository.BookedTripsRepository
import javax.inject.Inject

@HiltViewModel
class SpaceLaunchViewModel @Inject constructor(
    private val bookedTripsRepository: BookedTripsRepository,
    private val launchListInteractor: GetLaunchListInteractor,
    savedStateHandle: SavedStateHandle,
    @ApplicationId applicationId: String
) : AbstractMviViewModel<SpaceLaunchAction, SpaceLaunchViewState, SpaceLaunchCommand>(
    SpaceLaunchViewState.default,
    savedStateHandle = savedStateHandle,
    applicationId = applicationId
) {
    val viewEvents: MutableStateFlow<SpaceLaunchEvent?> = MutableStateFlow(null)

    init {
        viewModelScope.launch{
            bookedTripsRepository.bookedTripsFlow()
                .onStart { Timber.d("Connected Booked Trips Flow") }
                .onEach { Timber.d("Booked ${it?.tripsBooked} Trips") }
                .onCompletion { Timber.d("Completed Booked Trips Flow") }
                .filterNotNull()
                .collect {
                    viewEvents.tryEmit(
                         when (it.tripsBooked){
                            null -> SpaceLaunchEvent.SubscriptionError
                            1 -> SpaceLaunchEvent.TripBooked
                            else -> SpaceLaunchEvent.TripCancelled
                        }
                    )
                }
        }
    }

    override val reducer: Reducer<SpaceLaunchAction, SpaceLaunchViewState, SpaceLaunchCommand>
        get() = { action, state ->
            when (action) {
                is SpaceLaunchAction.RefreshLaunches -> state.copy(
                    isLoading = true
                ).then(
                    SpaceLaunchCommand.LoadLaunchList(action.cursor)
                )
                is SpaceLaunchAction.Initialise -> state.only()
                is SpaceLaunchAction.HandleLaunches -> state.copy(
                    isLoading = false,
                    launches = action.launches
                ).only()
            }
        }

    override fun executeCommand(command: SpaceLaunchCommand) {
        when (command) {
            SpaceLaunchCommand.BookTrip ->
                viewModelScope.launch {
                    // todo: implement or delete
                }

            is SpaceLaunchCommand.LoadLaunchList ->
                viewModelScope.launch {
                    launchListInteractor.invoke()?.let {
                        receiveAction(SpaceLaunchAction.HandleLaunches(it))
                    }
                }

        }
    }
}
