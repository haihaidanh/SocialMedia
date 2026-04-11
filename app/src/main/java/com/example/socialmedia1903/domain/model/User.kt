package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import java.util.Date

data class User(
    val username: String = "",
    val name: String="",
    val avatarUrl: String="",
    val id: String="",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val friendships: List<Friendship> = emptyList(),
    val role: String = "user",
    val status: String = "active"
)
