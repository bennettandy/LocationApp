package uk.co.avsoftware.authdata.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val uuid: UUID,
)
