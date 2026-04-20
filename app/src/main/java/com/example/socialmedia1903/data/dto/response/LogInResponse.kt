package com.example.socialmedia1903.data.dto.response

data class LogInResponse(
    val errCode: Int = 1,
    val message: String = "",
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val name: String? = null,
    val avatarUrl: String? = null,
    val username: String? = null,
    val id: String? = null
)
