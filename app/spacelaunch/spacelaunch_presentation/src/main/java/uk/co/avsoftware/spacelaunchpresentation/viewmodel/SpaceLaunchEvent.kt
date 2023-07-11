package uk.co.avsoftware.spacelaunchpresentation.viewmodel

import uk.co.avsoftware.core.mvi.MviViewEvent

sealed interface SpaceLaunchEvent : MviViewEvent {
    object TripBooked : SpaceLaunchEvent
    object TripCancelled : SpaceLaunchEvent
    object SubscriptionError : SpaceLaunchEvent
    object LoggedIn : SpaceLaunchEvent
    object LoadDetailsError : SpaceLaunchEvent
    object BookingFailed : SpaceLaunchEvent
    object CancelFailed : SpaceLaunchEvent
    object NavigateToLogin : SpaceLaunchEvent
}
