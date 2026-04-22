package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.domain.repository.GroupRepository
import javax.inject.Inject

class LeaveGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: String?){
        return groupRepository.leaveGroup(groupId)
    }
}