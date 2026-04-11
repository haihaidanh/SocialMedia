package com.example.socialmedia1903.data.dto.response

import com.example.socialmedia1903.domain.enums.InvitationStatus

data class SearchItemResponse(
    val id: String? = null,
    val name: String = "",
    val avatarUrl: String = "",
    val status: InvitationStatus
)
