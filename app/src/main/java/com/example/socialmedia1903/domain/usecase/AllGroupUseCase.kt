package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.Group
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class AllGroupUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(): List<Group>{
        return remoteDataSource.getGroups()
    }
}