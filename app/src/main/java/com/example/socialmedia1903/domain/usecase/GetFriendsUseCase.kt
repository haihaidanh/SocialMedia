package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(): List<FriendshipResponse>{
        return remoteDataSource.getFriends()
    }
}