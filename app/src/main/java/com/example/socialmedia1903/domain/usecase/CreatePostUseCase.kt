package com.example.socialmedia1903.domain.usecase

import android.content.Context
import androidx.core.net.toUri
import com.example.socialmedia1903.data.source.LocalDataSource
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.repository.PostRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val postRepository: PostRepository
) {


    suspend operator fun invoke(
        postId: String,
        content: String,
        type: PostType,
        groupId: String?,
        contentType: String,
        anonymous: Boolean,
        visibility: String,
    ) : Boolean{
        return remoteDataSource.createPost(
            postId = postId,
            content = content,
            type = type,
            groupId = groupId,
            contentType = contentType,
            anonymous = anonymous,
            visibility = visibility
        )
    }

    suspend fun uploadFile(context: Context,postId: String) : Boolean{
        return try{
            val images = localDataSource.getImages().map{ it.uri.toUri() }
            remoteDataSource.uploadImage(images, context, postId)
        }catch (e: Exception){
            e.printStackTrace()
            false
        }

    }
}