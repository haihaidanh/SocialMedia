package com.example.socialmedia1903.domain.repository

import com.example.socialmedia1903.domain.enums.InvitationType

interface InvitationRepository {
    suspend fun invitation(
        type: String, friendId: String?, groupId: String?
    )
    suspend fun acceptInvitation(
        type: InvitationType,
        groupId: String? = null,
        userId: String
    )
    suspend fun rejectInvitation(
        type: InvitationType,
        groupId: String? = null,
        userId: String
    )
}