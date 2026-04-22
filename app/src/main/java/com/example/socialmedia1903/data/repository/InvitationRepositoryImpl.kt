package com.example.socialmedia1903.data.repository

import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.enums.InvitationType
import com.example.socialmedia1903.domain.repository.InvitationRepository
import javax.inject.Inject

class InvitationRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : InvitationRepository {
    override suspend fun invitation(
        type: String, friendId: String?, groupId: String?
    ) {
        remoteDataSource.invitation(type, friendId, groupId)
    }

    override suspend fun acceptInvitation(
        type: InvitationType, groupId: String?, userId: String
    ) {
        remoteDataSource.acceptInvitation(type, groupId, userId)
    }

    override suspend fun rejectInvitation(
        type: InvitationType, groupId: String?, userId: String
    ) {
        remoteDataSource.rejectInvitation(type, groupId, userId)
    }
}