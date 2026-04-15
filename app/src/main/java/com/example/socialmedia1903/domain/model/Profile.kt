package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.domain.enums.InvitationStatus

data class Profile(
    val profile: ProfileInfo = ProfileInfo(),
    val status: InvitationStatus = InvitationStatus.NONE,
    val friends: List<User> = emptyList(),
    val posts: List<Post> = emptyList()
)
