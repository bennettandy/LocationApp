package uk.co.avsoftware.spacelaunchdata

sealed interface TripBookingResponse {
    object NotLoggedIn : TripBookingResponse
    data class BookingSuccess(val bookedTrips: BookTripMutation.BookTrips?) : TripBookingResponse
    data class CancelSuccess(val cancellation: CancelTripMutation.Data?) : TripBookingResponse
    object Error : TripBookingResponse
}
