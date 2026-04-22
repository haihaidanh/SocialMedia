package com.example.socialmedia1903.domain.repository

import com.example.socialmedia1903.data.dto.request.EditProfileRequest
import com.example.socialmedia1903.domain.model.Comment
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.model.Post
import org.json.JSONObject
import java.util.UUID

interface PostRepository {
    suspend fun enqueuePost(post: Post) : UUID
    suspend fun commentPost(postId: String, parentId: String?, content: String)
    suspend fun connectSocket(postId: String?, onNewComment: (JSONObject) -> Unit)
     suspend fun likePost(postId: String, type: String) : Like
     suspend fun getDetailPost(postId: String): Post
     suspend fun getAllComments(postId: String): List<Comment>
     suspend fun sharePost(postId: String, content: String)
     suspend fun deletePost(postId: String)
     suspend fun editPost(postId: String, editProfileRequest: EditProfileRequest)
     suspend fun undoDeletePost(postId: String)
}