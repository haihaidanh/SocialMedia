package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Group
import com.example.socialmedia1903.domain.model.GroupInfo
import javax.inject.Inject

class GetGroupDetailUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(groupId: String): Group {
        return remoteDataSource.getGroupDetail(groupId)
    }
}