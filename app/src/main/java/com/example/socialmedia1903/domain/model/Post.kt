package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.domain.enums.PostType
import java.util.Date

data class Post(
    val id: String = "",
    val authorId: String = "",
    val groupId: String? = null,
    val content: String = "",
    val type: PostType = PostType.TEXT,
    val contentType: String = "",
    val anonymous: Boolean = false,
    val visibility: String = "",
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val sharedCount: Int = 0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val user: User = User(),
    val comments: List<Comment> = emptyList(),
    val likes: List<Like> = emptyList(),
    val media: List<Media> = emptyList(),
    val group: GroupInfo? = null
)
