package com.example.socialmedia1903.domain.model

import com.example.socialmedia1903.domain.enums.InvitationStatus

data class SearchItem(
    val id: String? = null,
    val name: String = "",
    val avatarUrl: String = "",
    val status: InvitationStatus
)
