package uk.co.avsoftware.spacelaunchdomain.mapper

import uk.co.avsoftware.spacelaunchdata.LaunchListQuery
import uk.co.avsoftware.spacelaunchdomain.model.Launch
import uk.co.avsoftware.spacelaunchdomain.model.Launches
import uk.co.avsoftware.spacelaunchdomain.model.Mission

fun LaunchListQuery.Launches.mapToDomain() = Launches(
    cursor = cursor,
    launches = launches.map {
            launch ->
        launch?.let {
            Launch(
                id = launch.id,
                site = it.site,
                mission = it.mission?.let {
                    Mission(
                        name = it.name,
                        missionPatch = it.missionPatch,
                    )
                },
            )
        }
    },
    hasMore = hasMore,
)
