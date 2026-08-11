package com.example.socialmedia1903.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.domain.model.Comment
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.model.Media
import com.example.socialmedia1903.domain.model.User
import java.util.Date

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val authorId: String,
    val groupId: String?,
    val content: String?,
    val type: PostType,
    val contentType: String,
    val anonymous: Boolean,
    val visibility: PostVisibility,
    val likeCount: Int,
    val commentCount: Int,
    val sharedCount: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val user: User,
    val comments: List<Comment>,
    val likes: List<Like>,
    val media: List<Media>,
    val group: GroupInfo?,
    val isOwnPost: Boolean,
    val background: String?,
    val page: Int,
    val order: Int
)