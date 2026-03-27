package com.example.socialmedia1903.data.dto.response

import java.util.Date

data class PostResponse(
    val id: String = "",
    val authorId: String = "",
    val groupId: String = "",
    val content: String = "",
    val type: String = "",
    val contentType: String = "",
    val anonymous: Boolean = false,
    val visibility: String = "",
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val sharedCount: Int = 0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val User: UserResponse = UserResponse(),
    val Comments: List<CommentResponse> = emptyList(),
    val Likes: List<LikeResponse> = emptyList()
)
