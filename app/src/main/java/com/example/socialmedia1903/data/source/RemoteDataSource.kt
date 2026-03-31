package com.example.socialmedia1903.data.source

import com.example.socialmedia1903.data.dto.request.CommentRequest
import com.example.socialmedia1903.data.dto.request.LikeRequest
import com.example.socialmedia1903.data.dto.request.LogInRequest
import com.example.socialmedia1903.data.dto.request.SignUpRequest
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.dto.response.ListCommentsResponse
import com.example.socialmedia1903.data.dto.response.LogInResponse
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.dto.response.SearchResponse
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.model.Post
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val appService: AppService
) {
    suspend fun signUp(name: String, password: String, gender: Int) =
        appService.signUp(SignUpRequest(name, password, gender))

    suspend fun logIn(name: String, password: String): LogInResponse{
        return appService.logIn(LogInRequest(name, password))
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
}