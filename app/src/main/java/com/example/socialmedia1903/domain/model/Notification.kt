package com.example.socialmedia1903.domain.model

data class Notification(
    val id: String="",
    val authorId: String="",
    val postId: String?=null,
    val friendId: String?=null,
    val type: String="",
    val from: String="",
    val createdAt: String="",
    val updatedAt: String=""
)

