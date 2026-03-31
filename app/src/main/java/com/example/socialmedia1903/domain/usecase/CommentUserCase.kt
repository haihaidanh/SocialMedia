package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class CommentUserCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(postId: String, parentId: String?, content: String){
        remoteDataSource.commentPost(postId, parentId, content)
    }
}