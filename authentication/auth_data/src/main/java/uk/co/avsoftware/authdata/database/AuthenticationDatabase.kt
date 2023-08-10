package database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.co.avsoftware.authdata.dao.AuthenticationDao
import uk.co.avsoftware.authdata.entities.User

@Database(
    entities = [User::class],
    version = 1,
)
abstract class AuthenticationDatabase : RoomDatabase() {
    abstract val authenticationDao: AuthenticationDao
}
