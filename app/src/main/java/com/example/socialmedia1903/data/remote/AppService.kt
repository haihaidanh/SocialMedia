package com.example.socialmedia1903.data.remote

import com.example.socialmedia1903.data.dto.request.AddFriendRequest
import com.example.socialmedia1903.data.dto.request.CommentRequest
import com.example.socialmedia1903.data.dto.request.CreatePostRequest
import com.example.socialmedia1903.data.dto.request.LikeRequest
import com.example.socialmedia1903.data.dto.request.LogInRequest
import com.example.socialmedia1903.data.dto.request.SignUpRequest
import com.example.socialmedia1903.data.dto.response.DetailPostResponse
import com.example.socialmedia1903.data.dto.response.FriendsResponse
import com.example.socialmedia1903.data.dto.response.GroupListResponse
import com.example.socialmedia1903.data.dto.response.Group
import com.example.socialmedia1903.data.dto.response.GroupResponse
import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.dto.response.ListCommentsResponse
import com.example.socialmedia1903.data.dto.response.LogInResponse
import com.example.socialmedia1903.data.dto.response.NotificationListResponse
import com.example.socialmedia1903.data.dto.response.PostsResponse
import com.example.socialmedia1903.data.dto.response.ProfileResponse
import com.example.socialmedia1903.data.dto.response.RefreshTokenResponse
import com.example.socialmedia1903.data.dto.response.SearchResponse
import com.example.socialmedia1903.data.dto.response.SignUpResponse
import com.example.socialmedia1903.data.dto.response.UserResponse
import com.example.socialmedia1903.data.utils.NotificationType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {
    @Multipart
    @POST("/user/sign-up")
    suspend fun signUp(
        @Part file: MultipartBody.Part,
        @Query("name") name: String,
        @Query("password") password: String,
        @Query("gender") gender: Int
    ): Response<SignUpResponse>

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

    @POST("/comment-post")
    suspend fun commentPost(
        @Body commentRequest: CommentRequest
    )

    @Multipart
    @POST("/upload-post-images")
    suspend fun uploadImage(
        @Part files: List<MultipartBody.Part>,
        @Query("postId") postId: String
    ) : Response<Unit>

    @POST("/create-post")
    suspend fun createPost(
        @Body createPostRequest: CreatePostRequest
    ) : Response<Unit>

    @Multipart
    @POST("/create-group")
    suspend fun createGroup(
        @Part file: MultipartBody.Part,
        @Query("name") name: String,
        @Query("status") status: String
    ) : Response<Unit>

    @GET("/groups")
    suspend fun getGroups() : Response<GroupListResponse>

    @GET("/group/{groupId}")
    suspend fun getGroupDetail(
        @Path("groupId") groupId: String,
    ) : Response<GroupResponse>

    @GET("/profile/{id}")
    suspend fun getProfile(
        @Path("id") id: String,
    ): Response<ProfileResponse>

    @GET("/my-profile")
    suspend fun getMyProfile(): Response<ProfileResponse>

    @GET("/friend")
    suspend fun getFriends() : Response<FriendsResponse>

    @POST("/token")
    fun sendToken(
        @Query("token") token: String
    ) : Response<Unit>

    @POST("/invitation")
    suspend fun invitation(
        @Query("type") type: String,
        @Body addFriendRequest: AddFriendRequest
    ) : Response<Unit>

    @GET("/notifications")
    suspend fun getNotifications(): Response<NotificationListResponse>

    @POST("/accept-invitation")
    suspend fun acceptInvitation(
        @Query("type") type: NotificationType,
        @Query("groupId") groupId: String? = null
    ) : Response<Unit>

    @DELETE("/unfriend/{friendId}")
    suspend fun unFriend(
        @Path("friendId") friendId: String
    ): Response<Unit>
}