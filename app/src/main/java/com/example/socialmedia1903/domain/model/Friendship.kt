package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.data.dto.response.UserResponse
import java.util.Date

data class Friendship(
    val userId: String="",
    val friendId: String="",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val friend: User = User()
)
