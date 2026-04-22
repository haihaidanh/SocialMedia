package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.domain.model.Comment
import com.example.socialmedia1903.domain.repository.PostRepository
import javax.inject.Inject

class GetAllCommentUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String): List<Comment> {
        return postRepository.getAllComments(postId)
    }
}