package com.example.socialmedia1903.data.dto.response

data class GroupResponse(
    val group: Group,
    val posts: List<PostResponse>
)
