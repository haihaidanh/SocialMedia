package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.Group
import com.example.socialmedia1903.data.dto.response.NotificationResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(): List<NotificationResponse>{
        return remoteDataSource.getNotifications()
    }
}