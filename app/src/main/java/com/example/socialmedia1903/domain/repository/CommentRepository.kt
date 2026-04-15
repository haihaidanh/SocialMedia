package com.example.socialmedia1903.domain.repository

import com.example.socialmedia1903.data.remote.SocketManager
import org.json.JSONObject
import javax.inject.Inject

class CommentRepository @Inject constructor(private val socketManager: SocketManager) {

    fun connectSocket(postId: String?, onNewComment: (JSONObject) -> Unit) {
        socketManager.connect(postId, onNewComment)
    }
}