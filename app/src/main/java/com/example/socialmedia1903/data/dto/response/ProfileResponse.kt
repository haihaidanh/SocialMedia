package com.example.socialmedia1903.data.dto.response

import com.example.socialmedia1903.data.utils.InvitationStatus

data class ProfileResponse(
    val profile: Profile,
    val status: InvitationStatus,
    val friends: List<UserResponse>
)
