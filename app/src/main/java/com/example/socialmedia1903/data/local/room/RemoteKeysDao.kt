package com.example.socialmedia1903.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialmedia1903.data.local.enitity.RemoteKeys

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE song_id = :songId")
    suspend fun getRemoteKey(songId: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}