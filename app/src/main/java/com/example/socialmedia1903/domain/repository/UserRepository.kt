package com.example.socialmedia1903.domain.repository

import android.content.Context
import android.net.Uri
import com.example.socialmedia1903.domain.model.LogIn
import com.example.socialmedia1903.domain.model.SignUp

interface UserRepository {
    suspend fun login(username: String, password: String): LogIn
    suspend fun register(
        uri: Uri,
        username: String,
        email: String,
        password: String,
        context: Context,
        gender: Int
    ): SignUp

    suspend fun logout()
    suspend fun getUserId(): String
    suspend fun getUsername(): String
    suspend fun getAvatarUrl(): String
    suspend fun editBackground(uri: Uri)

}