package com.example.socialmedia1903.presentation.screen.detailpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.usecase.LikePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val likePostUseCase: LikePostUseCase,
    private val remoteDataSource: RemoteDataSource
): ViewModel() {

    private val _like = MutableStateFlow(LikeResponse())
    val like: StateFlow<LikeResponse> = _like

    private val _post = MutableStateFlow(PostResponse())
    val post: StateFlow<PostResponse> = _post

    private val _comments = MutableStateFlow<List<CommentResponse>>(emptyList())
    val comments: StateFlow<List<CommentResponse>> = _comments

    private val _isLoadingComments = MutableStateFlow(false)
    val isLoadingComments: StateFlow<Boolean> = _isLoadingComments

    private val _likeIcon = MutableStateFlow(R.drawable.like)
    val likeIcon: StateFlow<Int> = _likeIcon


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

}