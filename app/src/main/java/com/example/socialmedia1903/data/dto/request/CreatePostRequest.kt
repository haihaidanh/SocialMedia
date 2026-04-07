package com.example.socialmedia1903.data.dto.request

import java.util.Date

data class CreatePostRequest(
    val id: String = "",
    val groupId: String? = null,
    val content: String = "",
    val type: String = "",
    val contentType: String = "",
    val anonymous: Boolean = false,
    val visibility: String = "",
)
