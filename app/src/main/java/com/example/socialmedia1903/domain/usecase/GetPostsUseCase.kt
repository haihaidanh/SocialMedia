package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.source.PostRemoteMediator
import com.example.socialmedia1903.domain.model.Post
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
   private val pagingSource: PostRemoteMediator
) {
    suspend operator fun invoke(page: Int): List<Post>{
        return emptyList()
    }
}