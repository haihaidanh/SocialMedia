package com.example.socialmedia1903.infrastructure.di

import android.content.Context
import androidx.room.Room
import com.example.socialmedia1903.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigrationFrom()
            .build()
    }

    @Provides
    @Singleton
    fun provideImageDao(appDatabase: AppDatabase) = appDatabase.imageDao()
}