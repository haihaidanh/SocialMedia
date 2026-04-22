package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.domain.repository.InvitationRepository
import javax.inject.Inject

class SendInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {
    suspend operator fun invoke(
        type: String, friendId: String?, groupId: String?
    ){
        return invitationRepository.invitation(type, friendId, groupId)
    }
}