package com.example.socialmedia1903.data.dto.response

data class GroupInfoResponse(
    val id: String="",
    val name: String="",
    val status: String="",
    val imageUrl: String="",
    val creatorId: String="",
    val createdAt: String="",
    val updatedAt: String="",
    val isOwner: Boolean=false,
    val memberCount: Int=0,
    val MemberGroups: List<MemberResponse> = emptyList(),
    val statusRequest: String=""
)

