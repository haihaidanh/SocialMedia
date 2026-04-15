package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.data.dto.response.UserResponse
import java.util.Date

data class Comment(
    val id: String = "",
    val userId: String = "",
    val postId: String = "",
    val parentId: String? = "",
    val content: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val user: User = User()
)
