package com.example.socialmedia1903.data.dto.response


import java.util.Date

data class UserResponse(
    val username: String = "",
    val name: String="",
    val avatarUrl: String="",
    val id: String="",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val Friendships: List<FriendshipResponse> = emptyList(),
    val role: String = "user",
    val status: String = "active"
)
