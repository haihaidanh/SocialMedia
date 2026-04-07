package com.example.socialmedia1903.data.dto.response

import java.util.Date

data class FriendshipResponse(
    val userId: String="",
    val friendId: String="",
    val createdAt: Date= Date(),
    val updatedAt: Date= Date(),
    val friend: UserResponse = UserResponse()
)

