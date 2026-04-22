package com.example.socialmedia1903.data.repository

import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Friendship
import com.example.socialmedia1903.domain.repository.FriendRepository
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : FriendRepository {
    override suspend fun getAllFriends(): List<Friendship> {
        return remoteDataSource.getFriends()
    }

    override suspend fun unFriend(friendId: String) {
        remoteDataSource.unFriend(friendId)
    }
}