package com.example.socialmedia1903.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialmedia1903.data.local.enitity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY `order` ASC")
    fun pagingSource(): PagingSource<Int, PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("DELETE FROM posts")
    suspend fun clearAll()
}