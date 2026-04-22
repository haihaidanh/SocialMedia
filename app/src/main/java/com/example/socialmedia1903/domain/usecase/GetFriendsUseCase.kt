package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Friendship
import com.example.socialmedia1903.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(): List<Friendship>{
        return friendRepository.getAllFriends()
    }
}