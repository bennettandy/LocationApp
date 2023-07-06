package uk.co.avsoftware.spacelaunch_domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchDetails(
    val id: String,
    val site: String?,
    val mission: Mission?,
    val rocket: Rocket?,
    val isBooked: Boolean,
): Parcelable

@Parcelize
data class Rocket(
    val name: String?,
    val type: String?,
): Parcelable
