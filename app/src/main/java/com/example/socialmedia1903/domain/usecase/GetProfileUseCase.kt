package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.ProfileResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(id: String) : ProfileResponse {
        return remoteDataSource.getProfile(id)
    }

    suspend fun getMyProfile(): ProfileResponse{
        return remoteDataSource.getMyProfile()
    }
}