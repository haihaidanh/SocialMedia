package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.domain.repository.PostRepository
import javax.inject.Inject

class UndoDeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String) {
        postRepository.undoDeletePost(postId)
    }
}