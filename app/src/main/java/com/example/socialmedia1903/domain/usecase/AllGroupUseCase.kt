package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.GroupInfoResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.domain.repository.GroupRepository
import javax.inject.Inject

class AllGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(): List<GroupInfo>{
        return groupRepository.getAllGroups()
    }
}