package com.example.socialmedia1903.data.dto.response

data class Profile(
    val id: String="",
    val userId: String="",
    val name: String="",
    val avatarUrl: String="",
    val background: String?=null,
    val gender: Int = 0,
    val birthday: String?=null,
    val phoneNumber: String?=null,
    val address: String?=null,
    val createdAt: String="",
    val updatedAt: String="",
    val friendships: List<FriendshipResponse> = emptyList(),
)

