package com.example.socialmedia1903.domain.repository

import com.example.socialmedia1903.domain.model.Post
import java.util.UUID

interface PostRepository {
    suspend fun enqueuePost(post: Post) : UUID
}