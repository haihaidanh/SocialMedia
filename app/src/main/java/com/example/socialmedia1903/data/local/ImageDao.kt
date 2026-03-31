package com.example.socialmedia1903.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageEntity)

    @Query("SELECT * FROM images WHERE postId = :postId")
    suspend fun getImagesByPostId(postId: String): List<ImageEntity>

    @Query("DELETE FROM images")
    suspend fun deleteAllImages()
}