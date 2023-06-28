package uk.co.avsoftware.locationdomain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationEvent(
    val latitude: Double,
    val longitude: Double
) : Parcelable
