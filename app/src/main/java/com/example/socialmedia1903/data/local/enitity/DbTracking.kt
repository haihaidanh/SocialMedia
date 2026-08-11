package com.example.socialmedia1903.data.local.enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_tracking")
data class DbTracking(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tracking_id")
    val id: Int = 0,
    @ColumnInfo(name = "last_update")
    val lastUpdate: Long = 0L
)
