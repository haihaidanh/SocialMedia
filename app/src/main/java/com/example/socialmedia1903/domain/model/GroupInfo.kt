package com.example.socialmedia1903.domain.model

data class GroupInfo(
    val id: String="",
    val name: String="",
    val status: String="",
    val imageUrl: String="",
    val creatorId: String="",
    val createdAt: String="",
    val updatedAt: String="",
    val isOwner: Boolean=false,
    val memberCount: Int=0,
    val memberGroups: List<Member> = emptyList(),
    val statusRequest: String=""
)
