package com.example.socialmedia1903.domain.repository

import com.example.socialmedia1903.domain.model.Post

interface PostRepository {
    fun  enqueuePost(post: Post)
}