package com.example.socialmedia1903.data.local.enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo(name = "song_id")
    val id: String,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,
    @ColumnInfo(name = "next_key")
    val nextKey: Int?
)
