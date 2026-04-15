package com.example.socialmedia1903.data.dto.request

import com.example.socialmedia1903.domain.enums.PostType

data class PostRequest(
    val id: String = "",
    val groupId: String? = null,
    val content: String = "",
    val type: PostType = PostType.TEXT,
    val contentType: String = "",
    val anonymous: Boolean = false,
    val visibility: String = "",
)
