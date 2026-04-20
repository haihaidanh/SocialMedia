package com.example.socialmedia1903.data.remote

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class SocketManager {

    private var socket: Socket? = null

    fun connect(postId: String?, callback: (JSONObject) -> Unit) {
        val opts = IO.Options().apply {
            forceNew = true
            reconnection = true
        }

        socket = IO.socket("http://10.0.2.2:8080", opts)

        socket?.on(Socket.EVENT_CONNECT) {
            Log.d("SOCKET", "CONNECTED")
            socket?.emit("join_post", postId)
        }

        socket?.on("new_comment") { args ->
            Log.d("SOCKET", "NEW COMMENT RECEIVED")
            val data = args[0] as JSONObject
            callback(data)
        }

        socket?.connect()
    }

//    fun joinPost(postId: String?) {
//        socket?.on(Socket.EVENT_CONNECT) {
//            socket?.emit("join_post", postId)
//        }
//    }
//
//    fun listenNewComment(callback: (JSONObject) -> Unit) {
//        socket?.on("new_comment") { args ->
//            val data = args[0] as JSONObject
//            callback(data)
//        }
//    }
//
//    fun disconnect() {
//        socket?.disconnect()
//    }
}