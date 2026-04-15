package com.example.socialmedia1903.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.socialmedia1903.data.dto.response.SignUpResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(
        image: Uri,
        name: String,
        username: String,
        password: String,
        gender: Int,
        context: Context
    ): SignUpResponse {
        return remoteDataSource.signUp(image, name, username, password, gender, context)
    }
}