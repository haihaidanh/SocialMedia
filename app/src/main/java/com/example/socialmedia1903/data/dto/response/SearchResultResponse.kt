package com.example.socialmedia1903.data.dto.response

data class SearchResultResponse(
    val users: List<SearchItemResponse>,
    val posts: List<PostResponse>,
)
