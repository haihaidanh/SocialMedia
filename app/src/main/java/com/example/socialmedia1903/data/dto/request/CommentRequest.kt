package com.example.socialmedia1903.data.dto.request

data class CommentRequest(
    val postId: String="",
    val parentId: String?=null,
    val content: String=""
)
