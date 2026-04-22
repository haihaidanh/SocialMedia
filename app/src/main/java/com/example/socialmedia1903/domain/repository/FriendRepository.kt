package com.example.socialmedia1903.domain.repository

import com.example.socialmedia1903.domain.model.Friendship

interface FriendRepository {
    suspend fun getAllFriends(): List<Friendship>
    suspend fun unFriend(friendId: String)
}