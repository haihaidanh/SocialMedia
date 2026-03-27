package com.example.socialmedia1903.domain.model

import java.util.Date

data class Comment(
    val id: String,
    val userId: String,
    val postId: String,
    val parentId: String?,
    val content: String,
    val createdAt: Date,
    val updatedAt: Date
)
