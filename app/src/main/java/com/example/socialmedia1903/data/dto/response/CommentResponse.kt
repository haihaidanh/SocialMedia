package com.example.socialmedia1903.data.dto.response

import java.util.Date

data class CommentResponse(
    val id: String = "",
    val userId: String = "",
    val postId: String = "",
    val parentId: String? = "",
    val content: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val User: UserResponse = UserResponse()
)
