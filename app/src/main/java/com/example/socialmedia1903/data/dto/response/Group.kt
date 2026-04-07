package com.example.socialmedia1903.data.dto.response

data class Group(
    val id: String="",
    val name: String="",
    val status: String="",
    val imageUrl: String="",
    val creatorId: String="",
    val createdAt: String="",
    val updatedAt: String="",
    val isOwner: Boolean=false,
    val MemberGroups: List<MemberResponse> = emptyList(),
    val statusRequest: String=""
)

