package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class InvitationUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(type: String, friendId: String?, groupId: String?){
        remoteDataSource.invitation(
            type,
            friendId,
            groupId
        )
    }
}