package com.example.socialmedia1903.data.worker

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.socialmedia1903.data.dto.request.PostRequest
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.data.utils.AppUtils
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CreatePostWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val appService: AppService
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val id = inputData.getString("id")
            val content = inputData.getString("content")
            val type = inputData.getString("type")
                ?.let { runCatching { PostType.valueOf(it) }.getOrNull() }
            val groupId = inputData.getString("groupId")
            val contentType = inputData.getString("contentType")
            val anonymous = inputData.getBoolean("anonymous", false)
            val visibility = inputData.getString("visibility")
                ?.let { runCatching { PostVisibility.valueOf(it) }.getOrNull() }
            val images = inputData.getStringArray("images") ?: emptyArray()
            Log.d("hai", images.joinToString(","))
            val multiparts = images.map {
                AppUtils.uriToMultipart(
                    context = applicationContext,
                    it.toUri()
                )
            }

            val response = appService.createPost(
                PostRequest(
                    id = id ?: "",
                    content = content ?: "",
                    type = type ?: PostType.TEXT,
                    groupId = groupId,
                    contentType = contentType ?: "",
                    anonymous = anonymous,
                    visibility = visibility ?:  PostVisibility.PUBLIC
                )
            )
            if(images.isNotEmpty()) {
                appService.uploadImage(
                    multiparts, id ?: ""
                )
            }


            if (response.isSuccessful) {
                Result.success()
            } else {
                Result.retry()
            }

        } catch (e: Exception) {
            Result.retry()
        }
    }
}