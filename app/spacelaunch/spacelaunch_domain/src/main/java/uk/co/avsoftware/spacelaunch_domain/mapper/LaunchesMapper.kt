package uk.co.avsoftware.spacelaunch_domain.mapper

import com.example.rocketreserver.LaunchListQuery
import uk.co.avsoftware.spacelaunch_domain.model.Launch
import uk.co.avsoftware.spacelaunch_domain.model.Launches
import uk.co.avsoftware.spacelaunch_domain.model.Mission

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
                        missionPatch = it.missionPatch
                    )
                }
            )
        }
    },
    hasMore = hasMore
)