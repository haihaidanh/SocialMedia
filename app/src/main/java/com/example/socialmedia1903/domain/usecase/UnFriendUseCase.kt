package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.domain.repository.FriendRepository
import javax.inject.Inject

class UnFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(friendId: String){
        friendRepository.unFriend(friendId)
    }
}