package com.example.socialmedia1903.data.dto.response

data class GroupResponse(
    val group: GroupInfoResponse,
    val posts: List<PostResponse>
)
