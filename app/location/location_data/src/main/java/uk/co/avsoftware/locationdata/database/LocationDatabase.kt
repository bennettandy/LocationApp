package uk.co.avsoftware.locationdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.co.avsoftware.locationdata.dao.LocationDao
import uk.co.avsoftware.locationdata.entities.Location

@Database(
    entities = [Location::class],
    version = 1
)
abstract class LocationDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
}
