package com.example.socialmedia1903.data.dto.response.story

import com.example.socialmedia1903.data.dto.response.UserResponse

data class StoryResponse(
    val user: UserResponse = UserResponse(),
    val thumbnail: String="",
    val stories: List<Story> = emptyList()
)
