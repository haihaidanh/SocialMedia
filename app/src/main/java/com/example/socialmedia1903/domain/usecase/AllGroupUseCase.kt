package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.GroupInfoResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.GroupInfo
import javax.inject.Inject

class AllGroupUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(): List<GroupInfo>{
        return remoteDataSource.getGroups()
    }
}