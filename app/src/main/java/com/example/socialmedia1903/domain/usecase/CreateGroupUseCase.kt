package com.example.socialmedia1903.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(
        name: String,
        status: String,
        context: Context,
        avatarUri: Uri
    ) = remoteDataSource.createGroup(name, status, avatarUri, context)

}