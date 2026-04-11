package com.example.socialmedia1903.data.repository

import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

): PostRepository {
    override fun enqueuePost(post: Post) {
        TODO("Not yet implemented")
    }


}