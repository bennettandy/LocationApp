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
import uk.co.avsoftware.spacelaunch_domain.repository.BookedTripsRepository
import javax.inject.Inject

@HiltViewModel
class SpaceLaunchViewModel @Inject constructor(
    private val bookedTripsRepository: BookedTripsRepository,
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
                is SpaceLaunchAction.Initialise -> state.only()

            }
        }

    override fun executeCommand(command: SpaceLaunchCommand) {
        when (command) {
            SpaceLaunchCommand.BookTrip ->
                viewModelScope.launch {
//                    receiveAction(
//                        SpaceLaunchAction.UpdateLocationPermissions(
//                            coarse = locationPermissionInteractor.coarseLocationEnabled(),
//                            fine = locationPermissionInteractor.fineLocationEnabled(),
//                            gpsActivity = isGPSEnabledInteractor()
//                        )
//                    )
                }


        }
    }


    companion object {
        private const val COARSE_PERMISSION = android.Manifest.permission.ACCESS_COARSE_LOCATION
        private const val FINE_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION
    }
}
