package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.ProfileResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Profile
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(id: String) : Profile {
        return remoteDataSource.getProfile(id)
    }

    suspend fun getMyProfile(): Profile{
        return remoteDataSource.getMyProfile()
    }
}