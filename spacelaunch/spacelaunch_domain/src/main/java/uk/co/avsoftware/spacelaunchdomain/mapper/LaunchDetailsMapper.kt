package uk.co.avsoftware.spacelaunchdomain.mapper

import uk.co.avsoftware.spacelaunchdata.LaunchDetailsQuery
import uk.co.avsoftware.spacelaunchdomain.model.LaunchDetails
import uk.co.avsoftware.spacelaunchdomain.model.Mission
import uk.co.avsoftware.spacelaunchdomain.model.Rocket

internal fun LaunchDetailsQuery.Launch.mapToDomain(): LaunchDetails {
    val launch = this
    return LaunchDetails(
        id = id,
        site = site,
        mission = mission?.let {
            Mission(
                name = launch.mission?.name,
                missionPatch = launch.mission?.missionPatch
            )
        },
        rocket = rocket?.let {
            Rocket(
                name = launch.rocket?.name,
                type = launch.rocket?.type
            )
        },
        isBooked = isBooked
    )
}
