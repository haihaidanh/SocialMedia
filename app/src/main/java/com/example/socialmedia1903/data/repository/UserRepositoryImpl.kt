package com.example.socialmedia1903.data.repository

import android.content.Context
import android.net.Uri
import com.example.socialmedia1903.data.local.preference.MyPreference
import com.example.socialmedia1903.data.mapper.HaiMapper.toLogIn
import com.example.socialmedia1903.data.mapper.HaiMapper.toSignUp
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.LogIn
import com.example.socialmedia1903.domain.model.SignUp
import com.example.socialmedia1903.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val myPreference: MyPreference,
    @ApplicationContext private val context: Context
) : UserRepository {
    override suspend fun login(username: String, password: String): LogIn {
        return remoteDataSource.logIn(username, password).toLogIn()
    }

    override suspend fun register(
        uri: Uri,
        username: String,
        email: String,
        password: String,
        context: Context,
        gender: Int
    ): SignUp {
        return remoteDataSource.signUp(
            uri = uri,
            name = username,
            username = email,
            password = password,
            context = context,
            gender = gender
        ).toSignUp()
    }

    override suspend fun logout() {
        remoteDataSource.logOut()
        myPreference.clear()
    }

    override suspend fun getUserId(): String {
        return myPreference.getUserId() ?: ""
    }

    override suspend fun getUsername(): String {
        return myPreference.getUsername() ?: ""
    }

    override suspend fun getAvatarUrl(): String {
        return myPreference.getAvatarUrl() ?: ""
    }

    override suspend fun editBackground(uri: Uri) {
        remoteDataSource.editBackground(uri, context)
    }
}