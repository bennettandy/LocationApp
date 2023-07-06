package uk.co.avsoftware.spacelaunch_data

import com.example.rocketreserver.BookTripMutation
import com.example.rocketreserver.CancelTripMutation

sealed interface TripBookingResponse {
    object NotLoggedIn : TripBookingResponse
    data class BookingSuccess(val bookedTrips: BookTripMutation.BookTrips?) : TripBookingResponse
    data class CancelSuccess(val cancellation: CancelTripMutation.Data?) : TripBookingResponse
    object Error : TripBookingResponse
}
