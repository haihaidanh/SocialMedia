package com.example.socialmedia1903.domain.model

data class ProfileInfo(
    val id: String="",
    val userId: String="",
    val name: String="",
    val avatarUrl: String="",
    val background: String?=null,
    val gender: Int = 0,
    val birthday: String?=null,
    val username: String="",
    val phoneNumber: String?=null,
    val address: String?=null,
    val createdAt: String="",
    val updatedAt: String="",
    val friendships: List<Friendship> = emptyList(),
)
