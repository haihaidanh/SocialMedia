package com.example.socialmedia1903.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialmedia1903.data.local.enitity.DbTracking

@Dao
interface DbTrackingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbTracking: DbTracking)

    @Query("SELECT * FROM db_tracking ORDER BY last_update LIMIT 1")
    suspend fun getDbTacking(): DbTracking?

    @Query("DELETE FROM db_tracking")
    suspend fun deleteAll()
}