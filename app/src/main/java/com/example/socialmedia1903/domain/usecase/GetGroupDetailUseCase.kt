package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.Group
import com.example.socialmedia1903.data.dto.response.GroupResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class GetGroupDetailUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(groupId: String): GroupResponse{
        return remoteDataSource.getGroupDetail(groupId)
    }
}