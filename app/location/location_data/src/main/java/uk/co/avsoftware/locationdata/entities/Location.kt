package uk.co.avsoftware.locationdata.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)
