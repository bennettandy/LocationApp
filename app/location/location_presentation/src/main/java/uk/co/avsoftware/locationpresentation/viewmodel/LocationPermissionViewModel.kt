package uk.co.avsoftware.locationpresentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import uk.co.avsoftware.common.annotation.ApplicationId
import uk.co.avsoftware.common.extensions.Reducer
import uk.co.avsoftware.common.mvi.AbstractMviViewModel
import uk.co.avsoftware.location.interactor.IsGPSEnabledInteractor
import uk.co.avsoftware.location.interactor.LocationPermissionEnabledInteractor
import uk.co.avsoftware.locationdomain.repository.AndroidLocationEventsRepository
import javax.inject.Inject

@HiltViewModel
class LocationPermissionViewModel @Inject constructor(
    private val locationPermissionInteractor: LocationPermissionEnabledInteractor,
    private val isGPSEnabledInteractor: IsGPSEnabledInteractor,
    private val locationEventsRepository: AndroidLocationEventsRepository,
    savedStateHandle: SavedStateHandle,
    @ApplicationId applicationId: String
) : AbstractMviViewModel<LocationPermissionAction, LocationPermissionViewState, LocationPermissionCommand>(
    LocationPermissionViewState.default,
    savedStateHandle = savedStateHandle,
    applicationId = applicationId
) {
    val viewEvents: MutableSharedFlow<LocationPermissionEvent> = MutableSharedFlow<LocationPermissionEvent>(replay = 0, extraBufferCapacity = 1, BufferOverflow.DROP_OLDEST)

    private var locationListenerJob: Job? = null

    override val reducer: Reducer<LocationPermissionAction, LocationPermissionViewState, LocationPermissionCommand>
        get() = { action, state ->
            when (action) {
                is LocationPermissionAction.RefreshCurrentPermissions -> state.then(
                    LocationPermissionCommand.RefreshCurrentPermissions
                )

                is LocationPermissionAction.UpdateLocationPermissions -> state.copy(
                    coarseLocationGranted = action.coarse,
                    fineLocationGranted = action.fine,
                    gpsIsActive = action.gpsActivity
                )
                    .only()

                is LocationPermissionAction.RequestPermissionClicked -> state.then(
                    LocationPermissionCommand.CheckPermissionsCommand
                )

                is LocationPermissionAction.ProcessPermissionResponse -> state.copy(
                    coarseLocationGranted = action.permissions[COARSE_PERMISSION] ?: false,
                    fineLocationGranted = action.permissions[FINE_PERMISSION] ?: false
                ).then(
                    LocationPermissionCommand.HandleDeniedPermissions(action.permissions)
                )

                is LocationPermissionAction.ToggleLocationListening -> state.copy(
                    locationToggleState = action.listening
                ).then(
                    LocationPermissionCommand.ToggleLocationEventListener(action.listening)
                )
                is LocationPermissionAction.ProcessLocationEvent -> state.copy(
                    locationEvents = listOf(action.event).plus(state.locationEvents).take(10)
                ).only()
            }
        }

    override fun executeCommand(command: LocationPermissionCommand) {
        when (command) {
            LocationPermissionCommand.RefreshCurrentPermissions ->
                viewModelScope.launch {
                    receiveAction(
                        LocationPermissionAction.UpdateLocationPermissions(
                            coarse = locationPermissionInteractor.coarseLocationEnabled(),
                            fine = locationPermissionInteractor.fineLocationEnabled(),
                            gpsActivity = isGPSEnabledInteractor()
                        )
                    )
                }

            LocationPermissionCommand.CheckPermissionsCommand ->
                viewModelScope.launch {
                    viewEvents.tryEmit(
                        LocationPermissionEvent.ObtainPermissions(
                            permissions = listOf(
                                COARSE_PERMISSION,
                                FINE_PERMISSION
                            )
                        )
                    )
                }

            is LocationPermissionCommand.HandleDeniedPermissions ->
                viewModelScope.launch {
                    when {
                        command.permissions[COARSE_PERMISSION] == false -> LocationPermissionEvent.CoarsePermissionDenied
                        command.permissions[FINE_PERMISSION] == false -> LocationPermissionEvent.FinePermissionDenied
                        else -> null
                    }?.let {
                        viewEvents.tryEmit(it)
                    }
                }

            is LocationPermissionCommand.ToggleLocationEventListener ->
                viewModelScope.launch {
                    if (command.listening) {
                        startLocationListener()
                    } else {
                        stopLocationListener()
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationListener() {
        if (locationListenerJob?.isActive == true) {
            Timber.d("Location listener is already active")
        } else {
            Timber.d("Starting new Listener coroutine")
            locationListenerJob = viewModelScope.launch {
                locationEventsRepository().collect {
                    Timber.d("Location event: $it")
                    receiveAction(LocationPermissionAction.ProcessLocationEvent(it))
                }
            }
            viewEvents.tryEmit(LocationPermissionEvent.ListenerStarted)
        }
    }

    private fun stopLocationListener() {
        locationListenerJob?.cancel()
        viewEvents.tryEmit(LocationPermissionEvent.ListenerStopped)
    }

    companion object {
        private const val COARSE_PERMISSION = android.Manifest.permission.ACCESS_COARSE_LOCATION
        private const val FINE_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION
    }
}
