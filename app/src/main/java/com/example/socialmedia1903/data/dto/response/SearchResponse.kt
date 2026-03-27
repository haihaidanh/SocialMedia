package com.example.socialmedia1903.data.dto.response

import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.model.User

data class SearchResponse(
    val users: List<User>,
    val posts: List<Post>,
)
