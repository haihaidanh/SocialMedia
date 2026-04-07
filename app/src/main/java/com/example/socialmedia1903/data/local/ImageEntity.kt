package com.example.socialmedia1903.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey
    val uri: String
)
