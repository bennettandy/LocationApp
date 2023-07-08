package uk.co.avsoftware.spacelaunchpresentation.viewmodel

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
import uk.co.avsoftware.spacelaunchdomain.interactor.BookLaunchInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.CancelLaunchBookingInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchDetailsInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.GetLaunchListInteractor
import uk.co.avsoftware.spacelaunchdomain.interactor.SpaceLaunchLoginInteractor
import uk.co.avsoftware.spacelaunchdomain.model.LaunchBookingResponse
import uk.co.avsoftware.spacelaunchdomain.model.SpaceLaunchDetailResponse
import uk.co.avsoftware.spacelaunchdomain.repository.BookedTripsRepository
import javax.inject.Inject

@HiltViewModel
class SpaceLaunchViewModel @Inject constructor(
    private val bookedTripsRepository: BookedTripsRepository,
    private val launchListInteractor: GetLaunchListInteractor,
    private val loginInteractor: SpaceLaunchLoginInteractor,
    private val launchDetailsInteractor: GetLaunchDetailsInteractor,
    private val bookLaunchInteractor: BookLaunchInteractor,
    private val cancelLaunchBookingInteractor: CancelLaunchBookingInteractor,
    savedStateHandle: SavedStateHandle,
    @ApplicationId applicationId: String
) : AbstractMviViewModel<SpaceLaunchAction, SpaceLaunchViewState, SpaceLaunchCommand>(
    SpaceLaunchViewState.default,
    savedStateHandle = savedStateHandle,
    applicationId = applicationId
) {
    val viewEvents: MutableStateFlow<SpaceLaunchEvent?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            bookedTripsRepository.bookedTripsFlow()
                .onStart { Timber.d("Connected Booked Trips Flow") }
                .onEach { Timber.d("Booked ${it?.tripsBooked} Trips") }
                .onCompletion { Timber.d("Completed Booked Trips Flow") }
                .filterNotNull()
                .collect {
                    viewEvents.tryEmit(
                        when (it.tripsBooked) {
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

                is SpaceLaunchAction.SetEmail -> state.copy(email = action.email).only()
                is SpaceLaunchAction.Login -> state.copy(
                    isLoading = true
                ).then(
                    SpaceLaunchCommand.Login(state.email)
                )

                is SpaceLaunchAction.SetLoggedIn -> state.copy(
                    isLoading = false,
                    isLoggedIn = action.loggedIn
                ).only()
                is SpaceLaunchAction.LoadDetails -> state.copy(
                    launchDetailsLoading = true
                ).then(
                    SpaceLaunchCommand.LoadLaunchDetails(action.launchId)
                )
                is SpaceLaunchAction.BookLaunch -> state.copy(
                    bookingInProgress = true
                ).then(
                    SpaceLaunchCommand.BookLaunch(
                        action.launchId
                    )
                )
                is SpaceLaunchAction.CancelLaunch -> state.copy(
                    bookingInProgress = true
                ).then(
                    SpaceLaunchCommand.CancelLaunch(
                        action.launchId
                    )
                )
                is SpaceLaunchAction.UpdateBookingState -> state.copy(
                    bookingInProgress = false,
                    launchDetails = state.launchDetails?.copy(isBooked = action.isBooked)
                ).only()
                is SpaceLaunchAction.HandleLaunchDetail -> state.copy(
                    launchDetailsLoading = false,
                    launchDetails = action.launchDetail
                ).only()
            }.also {
                    pair ->
                Timber.d("View State: ${pair.first}")
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

            is SpaceLaunchCommand.Login ->
                viewModelScope.launch {
                    command.email?.let {
                        val loggedIn = loginInteractor(command.email)
                        receiveAction(SpaceLaunchAction.SetLoggedIn(loggedIn))
                        if (loggedIn) {
                            viewEvents.tryEmit(SpaceLaunchEvent.LoggedIn)
                        }
                    }
                }

            is SpaceLaunchCommand.LoadLaunchDetails ->
                viewModelScope.launch {
                    when (val result = launchDetailsInteractor.invoke(command.launchId)) {
                        is SpaceLaunchDetailResponse.Success -> receiveAction(
                            SpaceLaunchAction.HandleLaunchDetail(result.launchDetails)
                        )
                        else -> {
                            viewEvents.tryEmit(
                                SpaceLaunchEvent.LoadDetailsError
                            )
                            receiveAction(SpaceLaunchAction.HandleLaunchDetail(null))
                        }
                    }
                }

            is SpaceLaunchCommand.BookLaunch ->
                viewModelScope.launch {
                    when (bookLaunchInteractor.invoke(command.launchId)) {
                        is LaunchBookingResponse.Success -> receiveAction(
                            SpaceLaunchAction.UpdateBookingState(
                                isBooked = true
                            )
                        )
                        is LaunchBookingResponse.Error -> viewEvents.tryEmit(SpaceLaunchEvent.BookingFailed)
                        is LaunchBookingResponse.NotLoggedIn -> viewEvents.tryEmit(SpaceLaunchEvent.NavigateToLogin)
                    }
                }
            is SpaceLaunchCommand.CancelLaunch ->
                viewModelScope.launch {
                    when (cancelLaunchBookingInteractor.invoke(command.launchId)) {
                        is LaunchBookingResponse.Success -> receiveAction(
                            SpaceLaunchAction.UpdateBookingState(
                                isBooked = false
                            )
                        )
                        is LaunchBookingResponse.Error -> viewEvents.tryEmit(SpaceLaunchEvent.CancelFailed)
                        is LaunchBookingResponse.NotLoggedIn -> viewEvents.tryEmit(SpaceLaunchEvent.NavigateToLogin)
                    }
                }
        }
    }
}
