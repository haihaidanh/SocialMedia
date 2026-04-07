package com.example.socialmedia1903.data.source

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.socialmedia1903.data.dto.request.AddFriendRequest
import com.example.socialmedia1903.data.dto.request.CommentRequest
import com.example.socialmedia1903.data.dto.request.CreatePostRequest
import com.example.socialmedia1903.data.dto.request.LikeRequest
import com.example.socialmedia1903.data.dto.request.LogInRequest
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import com.example.socialmedia1903.data.dto.response.Group
import com.example.socialmedia1903.data.dto.response.GroupResponse
import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.dto.response.LogInResponse
import com.example.socialmedia1903.data.dto.response.NotificationResponse
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.dto.response.Profile
import com.example.socialmedia1903.data.dto.response.ProfileResponse
import com.example.socialmedia1903.data.dto.response.SearchResponse
import com.example.socialmedia1903.data.dto.response.SignUpResponse
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.data.utils.AppUtils
import com.example.socialmedia1903.data.utils.InvitationStatus
import com.example.socialmedia1903.data.utils.NotificationType
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val appService: AppService
) {
    suspend fun signUp(uri: Uri, name: String, password: String, gender: Int, context: Context): SignUpResponse{
        val image = AppUtils.uriToMultipart(context, uri)
        val response = appService.signUp(
            image,
            name,
            password,
            gender
        )
        if(!response.isSuccessful){
            throw Exception("Failed to sign up")
        }
        return response.body() ?: SignUpResponse(1, "Failed to sign up")
    }

    suspend fun logIn(name: String, password: String): LogInResponse {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()

            val response = appService.logIn(
                LogInRequest(name, password, token)
            )

            response
        } catch (e: Exception) {
            Log.d("hai", "token failed: ${e.message}")
            throw e
        }
    }

    suspend fun search(text: String): SearchResponse{
        return appService.search(text)
    }

    suspend fun likePost(postId: String, type: String): LikeResponse{
        return appService.likePost(LikeRequest(postId, type))
    }

    suspend fun getDetailPost(postId: String): PostResponse{
        return appService.getDetailPost(postId).post
    }

    suspend fun getAllComments(postId: String): List<CommentResponse>{
        return appService.getAllComment(postId, "all").comments
    }

    suspend fun logOut(){
        appService.logOut()
    }

    suspend fun commentPost(postId: String, parentId: String?, content: String){
        appService.commentPost(CommentRequest(postId, parentId, content))
    }

    suspend fun createPost(
        postId: String,
        content: String,
        type: String,
        groupId: String?,
        contentType: String,
        anonymous: Boolean,
        visibility: String
    ): Boolean{
        val response = appService.createPost(
            CreatePostRequest(
                id = postId,
                content = content,
                groupId = groupId,
                type = type,
                contentType = contentType,
                anonymous = anonymous,
                visibility = visibility
            )
        )
        if (!response.isSuccessful){
            throw Exception("Failed to create post")
        }
        return true
    }

    suspend fun uploadImage(images: List<Uri>, context: Context, postId: String): Boolean{
        val multipart =  images.map { AppUtils.uriToMultipart(context, it) }
        val response = appService.uploadImage(multipart, postId)
        if (!response.isSuccessful){
            throw Exception("Failed to upload image")
        }
        return true
    }

    suspend fun createGroup(
        name: String,
        status: String,
        avatarUri: Uri,
        context: Context
    ): Boolean{
        val multipart = AppUtils.uriToMultipart(context, avatarUri)
        val response = appService.createGroup(multipart, name, status)
        if (!response.isSuccessful){
            throw Exception("Failed to create group")
        }
        return true
    }
    suspend fun getGroups(): List<Group>{
        val response = appService.getGroups()
        if (!response.isSuccessful){
            throw Exception("Failed to get groups")
        }
        return response.body()?.groups ?: emptyList()
    }

    suspend fun getGroupDetail(groupId: String): GroupResponse{
        val response = appService.getGroupDetail(groupId)
        if (!response.isSuccessful){
            throw Exception("Failed to get group detail")
        }
        return response.body() ?: GroupResponse(Group(), emptyList())
    }

    suspend fun getProfile(id: String): ProfileResponse {
        val response = appService.getProfile(id)
        if (!response.isSuccessful){
            throw Exception("Failed to get profile")
        }
        //Log.d("hai", "profile: ${response.body()?.status}")
        return response.body() ?: ProfileResponse(Profile(), InvitationStatus.NONE, emptyList())
    }

    suspend fun getMyProfile(): ProfileResponse {
        val response = appService.getMyProfile()
        if (!response.isSuccessful){
            throw Exception("Failed to get profile")
        }
        return response.body() ?: ProfileResponse(Profile(), InvitationStatus.NONE,emptyList())
    }

    suspend fun getFriends(): List<FriendshipResponse>{
        val response = appService.getFriends()
        if (!response.isSuccessful) {
            throw Exception("Failed to get friends")
        }
        return response.body()?.friends ?: emptyList()
    }

    suspend fun invitation(type: String, friendId: String?, groupId: String?){
        val response = appService.invitation(type, AddFriendRequest(friendId, groupId))
        if(!response.isSuccessful){
            throw Exception("Failed to send invitation")
        }
        return response.body() ?: Unit
    }

    suspend fun getNotifications(): List<NotificationResponse>{
        val response = appService.getNotifications()
        if (!response.isSuccessful){
            throw Exception("Failed to get notifications")
        }
        return response.body()?.notifications ?: emptyList()
    }

    suspend fun acceptInvitation(type: NotificationType, groupId: String? = null){
        val response = appService.acceptInvitation(type, groupId)
        if (!response.isSuccessful){
            throw Exception("Failed to accept invitation")
        }
        return response.body() ?: Unit
    }

    suspend fun unFriend(
        friendId: String
    ){
        val response = appService.unFriend(friendId)
        if (!response.isSuccessful){
            throw Exception("Failed to unfriend")
        }
        return response.body() ?: Unit
    }
}