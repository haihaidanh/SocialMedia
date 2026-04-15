package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.dto.response.SearchItemResponse

data class SearchResult(
    val users: List<SearchItem>,
    val posts: List<Post>,
)
