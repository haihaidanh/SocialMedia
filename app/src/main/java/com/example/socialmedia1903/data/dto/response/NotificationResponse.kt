package com.example.socialmedia1903.data.dto.response

import com.example.socialmedia1903.data.utils.NotificationType

data class NotificationResponse(
    val id: String = "",
    val userId: String = "",
    val destinationId: String = "",
    val type: NotificationType= NotificationType.ADD_FRIEND,
    val createdAt: String="",
    val updatedAt: String="",
    val User: UserResponse = UserResponse(),
    val groupId: String? = null,
    val groupAvatar: String? = null,
    val groupName: String? = null
)

