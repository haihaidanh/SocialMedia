package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class LikePostUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(postId: String, type: String): LikeResponse {
        return remoteDataSource.likePost(postId, type)
    }
}