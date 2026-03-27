package com.example.socialmedia1903.data.remote

import com.example.socialmedia1903.data.dto.request.LikeRequest
import com.example.socialmedia1903.data.dto.request.LogInRequest
import com.example.socialmedia1903.data.dto.request.SignUpRequest
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.DetailPostResponse
import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.dto.response.ListCommentsResponse
import com.example.socialmedia1903.data.dto.response.LogInResponse
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.dto.response.PostsResponse
import com.example.socialmedia1903.data.dto.response.RefreshTokenResponse
import com.example.socialmedia1903.data.dto.response.SearchResponse
import com.example.socialmedia1903.data.dto.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {
    @POST("/user/sign-up")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): SignUpResponse

    @POST("/user/log-in")
    suspend fun logIn(
        @Body logInRequest: LogInRequest
    ): LogInResponse

    @POST("/auth/refresh-token")
    fun refreshToken(
        @Body body: Map<String, String>
    ): Call<RefreshTokenResponse>

    @GET("/get-all-posts")
    suspend fun getPosts(
        @Query("page") page: Int
    ) : PostsResponse

    @GET("/search")
    suspend fun search(
        @Query("search") text: String
    ) : SearchResponse

    @POST("/like-post")
    suspend fun likePost(
        @Body likeRequest: LikeRequest
    ): LikeResponse

    @GET("/post/{postId}")
    suspend fun getDetailPost(
        @Path("postId") postId: String
    ): DetailPostResponse

    @GET("/get-all-comment/{postId}")
    suspend fun getAllComment(
        @Path("postId") postId: String,
        @Query("page") page: String
    ): ListCommentsResponse

    @GET("/log-out")
    suspend fun logOut()
}