package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.repository.PostRepository
import javax.inject.Inject

class CommentUserCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(postId: String, parentId: String?, content: String){
        postRepository.commentPost(postId, parentId, content)
    }
}