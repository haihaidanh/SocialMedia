package com.example.socialmedia1903.data.dto.response

data class SearchResponse(
    val users: List<SearchItemResponse>,
    val posts: List<PostResponse>,
)
