package uk.co.avsoftware.authdata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import uk.co.avsoftware.authdata.entities.User

@Dao
interface AuthenticationDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: User)

    @Upsert
    fun upsertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user ORDER BY uuid")
    fun getAllUsers(): Flow<List<User>>
}
