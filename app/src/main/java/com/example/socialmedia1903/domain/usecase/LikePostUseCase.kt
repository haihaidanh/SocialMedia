package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.repository.PostRepository
import javax.inject.Inject

class LikePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String, type: String): Like {
        return postRepository.likePost(postId, type)
    }
}