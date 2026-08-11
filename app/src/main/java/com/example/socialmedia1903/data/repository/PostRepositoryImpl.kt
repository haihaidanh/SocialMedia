package com.example.socialmedia1903.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.socialmedia1903.data.dto.request.EditProfileRequest
import com.example.socialmedia1903.data.local.enitity.PostEntity
import com.example.socialmedia1903.data.local.room.AppDatabase
import com.example.socialmedia1903.data.mapper.HaiMapper.toEntity
import com.example.socialmedia1903.data.mapper.HaiMapper.toPost
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.data.remote.SocketManager
import com.example.socialmedia1903.data.source.LocalDataSource
import com.example.socialmedia1903.data.source.PostRemoteMediator
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.data.worker.CreatePostWorker
import com.example.socialmedia1903.domain.model.Comment
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.repository.PostRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val socketManager: SocketManager,
    private val appDatabase: AppDatabase,
    private val appService: AppService
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
            "visibility" to post.visibility.name,
            "images" to images,
            "background" to post.background
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

    override suspend fun commentPost(postId: String, parentId: String?, content: String) {
        remoteDataSource.commentPost(postId, parentId, content)
    }

    override suspend fun connectSocket(postId: String?, onNewComment: (JSONObject) -> Unit) {
        socketManager.connect(postId, onNewComment)
    }

    override suspend fun likePost(postId: String, type: String) : Like {
        return remoteDataSource.likePost(postId, type)
    }

    override suspend fun getDetailPost(postId: String): Post {
        return remoteDataSource.getDetailPost(postId)
    }

    override suspend fun getAllComments(postId: String): List<Comment> {
        return remoteDataSource.getAllComments(postId)
    }

    override suspend fun sharePost(postId: String, content: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: String) {
        remoteDataSource.deletePost(postId)
    }

    override suspend fun editPost(postId: String, editProfileRequest: EditProfileRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun undoDeletePost(postId: String) {
        remoteDataSource.undoDeletePost(postId)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 2),
            remoteMediator = PostRemoteMediator(appDatabase, appService),
            pagingSourceFactory = { appDatabase.postDao().pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { post ->
                post.toPost()
            }
        }
    }

}