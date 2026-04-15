package com.example.socialmedia1903.presentation.screen.detailpost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.remote.SocketManager
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.repository.CommentRepository
import com.example.socialmedia1903.domain.usecase.CommentUserCase
import com.example.socialmedia1903.domain.usecase.GetFriendsUseCase
import com.example.socialmedia1903.domain.usecase.LikePostUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val likePostUseCase: LikePostUseCase,
    private val remoteDataSource: RemoteDataSource,
    private val commentRepository: CommentRepository,
    private val commentUserCase: CommentUserCase,
    private val getFriendsUseCase: GetFriendsUseCase
): ViewModel() {

    private val _like = MutableStateFlow(LikeResponse())
    val like: StateFlow<LikeResponse> = _like

    private val _post = MutableStateFlow(PostResponse())
    val post: StateFlow<PostResponse> = _post

    private val _comments = MutableStateFlow<List<CommentResponse>>(emptyList())
    val comments: StateFlow<List<CommentResponse>> = _comments

    private val _isLoadingComments = MutableStateFlow(false)
    val isLoadingComments: StateFlow<Boolean> = _isLoadingComments

    private val _friends = MutableStateFlow<List<FriendshipResponse>>(emptyList())
    val friends: StateFlow<List<FriendshipResponse>> = _friends


    fun likePost(postId: String, type: String){
        viewModelScope.launch {
            _like.value =  likePostUseCase(postId, type)
        }
    }

    fun getDetailPost(postId: String){
        viewModelScope.launch {
            _post.value = remoteDataSource.getDetailPost(postId)
        }
    }

    fun getAllComment(postId: String){
        viewModelScope.launch {
            _isLoadingComments.value = true
            _comments.value = remoteDataSource.getAllComments(postId)
            _isLoadingComments.value = false
        }

    }


    private val gson = Gson()

    fun commentPost(postId: String, parentId: String?, content: String){
        viewModelScope.launch {
            commentUserCase(postId, parentId, content)
        }
    }

    fun start(postId: String?) {
        Log.d("VM", "START CALLED: $postId")
        commentRepository.connectSocket(postId) { data ->
            Log.d("VM", "bla: $postId")
            val comment = gson.fromJson(
                data.toString(),
                CommentResponse::class.java
            )

            val newList = _comments.value.toMutableList()
            newList.add(comment)

            _comments.value = newList
            Log.d("VM", _comments.value.size.toString())
        }
    }

    fun getFriends(){
        viewModelScope.launch {
            _friends.value = getFriendsUseCase()
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        commentRepository.disconnectSocket()
//    }

}