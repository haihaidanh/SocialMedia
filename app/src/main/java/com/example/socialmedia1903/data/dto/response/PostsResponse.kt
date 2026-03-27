package com.example.socialmedia1903.data.dto.response

import com.example.socialmedia1903.domain.model.Post

data class PostsResponse(
    val errCode: Int,
    val errMessage: String,
    val posts: List<PostResponse>

)
