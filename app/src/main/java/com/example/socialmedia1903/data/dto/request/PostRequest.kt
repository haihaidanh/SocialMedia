package com.example.socialmedia1903.data.dto.request

import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.presentation.screen.createpost.PostBackground

data class PostRequest(
    val id: String = "",
    val groupId: String? = null,
    val content: String? = null,
    val type: PostType = PostType.TEXT,
    val contentType: String = "",
    val anonymous: Boolean = false,
    val background: String? = null,
    val visibility: PostVisibility = PostVisibility.PUBLIC,
)
