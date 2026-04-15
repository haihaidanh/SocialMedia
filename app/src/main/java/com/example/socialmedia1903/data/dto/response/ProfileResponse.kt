package com.example.socialmedia1903.data.dto.response

import com.example.socialmedia1903.domain.enums.InvitationStatus

data class ProfileResponse(
    val profile: ProfileInfoResponse = ProfileInfoResponse(),
    val status: InvitationStatus = InvitationStatus.NONE,
    val friends: List<UserResponse> = emptyList(),
    val posts: List<PostResponse> = emptyList()
)
