package uk.co.avsoftware.locationpresentation.viewmodel

import uk.co.avsoftware.core.mvi.MviViewEvent

sealed interface LocationPermissionEvent : MviViewEvent {
    data class ObtainPermissions(val permissions: List<String>) : LocationPermissionEvent
    object CoarsePermissionDenied : LocationPermissionEvent
    object FinePermissionDenied : LocationPermissionEvent

    object ListenerStarted : LocationPermissionEvent
    object ListenerStopped : LocationPermissionEvent
}
