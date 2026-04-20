package com.example.socialmedia1903.data.repository

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.socialmedia1903.data.source.LocalDataSource
import com.example.socialmedia1903.data.utils.CreatePostWorker
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.repository.PostRepository
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val localDataSource: LocalDataSource,
): PostRepository {
    override suspend fun enqueuePost(post: Post): UUID {

        val images = localDataSource.getImages()
            .map{ it.uri }.toTypedArray()

        val data = workDataOf(
            "id" to post.id,
            "content" to post.content,
            "type" to post.type.name,
            "groupId" to post.groupId,
            "contentType" to post.contentType,
            "anonymous" to post.anonymous,
            "visibility" to post.visibility,
            "images" to images
        )
        Log.d("hai", images.joinToString(","))
        val request = OneTimeWorkRequestBuilder<CreatePostWorker>()
            .setInputData(data)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(request)
        return request.id
    }


}