package com.example.socialmedia1903.domain.model

import java.util.Date

data class Like(
    val id: String,
    val userId: String,
    val postId: String,
    val type: String,
    val createdAt: Date,
    val updatedAt: Date
)
