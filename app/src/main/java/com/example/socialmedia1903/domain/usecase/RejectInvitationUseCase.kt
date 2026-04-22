package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.domain.enums.InvitationType
import com.example.socialmedia1903.domain.repository.InvitationRepository
import javax.inject.Inject

class RejectInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {
    suspend operator fun invoke(
        type: InvitationType, groupId: String? = null, userId: String
    ){
        return invitationRepository.rejectInvitation(type, groupId, userId)
    }
}