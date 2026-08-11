package com.example.socialmedia1903.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.socialmedia1903.data.local.converters.PostConverters
import com.example.socialmedia1903.data.local.enitity.DbTracking
import com.example.socialmedia1903.data.local.enitity.PostEntity
import com.example.socialmedia1903.data.local.enitity.RemoteKeys

@Database(
    entities = [
        ImageEntity::class,
        PostEntity::class,
        RemoteKeys::class,
        DbTracking::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(PostConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun postDao(): PostDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun dbTrackingDao(): DbTrackingDao

}