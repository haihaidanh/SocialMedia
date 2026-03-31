package com.example.socialmedia1903.infrastructure.di

import com.example.socialmedia1903.data.remote.SocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideSocketManager(): SocketManager {
        return SocketManager()
    }
}