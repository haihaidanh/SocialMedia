package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.domain.enums.NotificationType

data class Notification(
    val id: String = "",
    val userId: String = "",
    val destinationId: String = "",
    val resourceId: String? = null,
    val from: String = "",
    val type: NotificationType = NotificationType.ADD_FRIEND,
    val createdAt: String="",
    val updatedAt: String="",
    val user: User = User(),
    val groupId: String? = null,
    val groupAvatar: String? = null,
    val groupName: String? = null,
    val likeType: String? = null,
    val postContent: String? = null,
    val commentContent: String? = null
)

