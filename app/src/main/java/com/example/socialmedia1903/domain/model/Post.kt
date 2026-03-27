package com.example.socialmedia1903.domain.model

import java.util.Date

data class Post(
    val id: String,
    val authorId: String,
    val groupId: String,
    val content: String,
    val type: String,
    val contentType: String,
    val anonymous: Boolean,
    val visibility: String,
    val likeCount: Int,
    val commentCount: Int,
    val sharedCount: Int,
    val createdAt: Date,
    val updatedAt: Date
)
