package uk.co.avsoftware.spacelaunch_domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
public data class Launches(
    public val cursor: String?,
    public val launches: List<Launch?>,
    public val hasMore: Boolean
) : Parcelable

@Parcelize
public data class Launch(
    public val id: String,
    public val site: String?,
    public val mission: Mission?
) : Parcelable

@Parcelize
public data class Mission(
    public val name: String?,
    public val missionPatch: String?
) : Parcelable
