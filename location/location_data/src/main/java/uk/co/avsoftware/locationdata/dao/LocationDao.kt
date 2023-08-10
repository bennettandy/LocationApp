package uk.co.avsoftware.locationdata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import uk.co.avsoftware.locationdata.entities.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertLocation(locationEntity: Location)

    @Upsert
    fun upsertLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

    @Query("SELECT * FROM location ORDER BY id")
    fun getAllLocations(): Flow<List<Location>>
}
