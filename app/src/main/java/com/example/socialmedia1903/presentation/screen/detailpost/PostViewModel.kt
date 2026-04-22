package com.example.socialmedia1903.presentation.screen.detailpost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import com.example.socialmedia1903.data.mapper.HaiMapper.toComment
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.domain.model.Comment
import com.example.socialmedia1903.domain.model.Friendship
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.repository.PostRepository
import com.example.socialmedia1903.domain.usecase.CommentUserCase
import com.example.socialmedia1903.domain.usecase.DeletePostUseCase
import com.example.socialmedia1903.domain.usecase.GetAllCommentUseCase
import com.example.socialmedia1903.domain.usecase.GetDetailPostUseCase
import com.example.socialmedia1903.domain.usecase.GetFriendsUseCase
import com.example.socialmedia1903.domain.usecase.LikePostUseCase
import com.example.socialmedia1903.domain.usecase.UndoDeletePostUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val likePostUseCase: LikePostUseCase,
    private val commentUserCase: CommentUserCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getDetailPostUseCase: GetDetailPostUseCase,
    private val getAllCommentUseCase: GetAllCommentUseCase,
    private val postRepository: PostRepository,
    private val deletePostUseCase: DeletePostUseCase,
    private val undoDeletePostUseCase: UndoDeletePostUseCase
) : ViewModel() {

    private val _like = MutableStateFlow(Like())
    val like: StateFlow<Like> = _like

    private val _post = MutableStateFlow(Post())
    val post: StateFlow<Post> = _post

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    private val _isLoadingComments = MutableStateFlow(false)
    val isLoadingComments: StateFlow<Boolean> = _isLoadingComments

    private val _friends = MutableStateFlow<List<Friendship>>(emptyList())
    val friends: StateFlow<List<Friendship>> = _friends


    fun likePost(postId: String, type: String) {
        viewModelScope.launch {
            _like.value = likePostUseCase(postId, type)
        }
    }

    fun getDetailPost(postId: String) {
        viewModelScope.launch {
            _post.value = getDetailPostUseCase(postId)
        }
    }

    fun getAllComment(postId: String) {
        viewModelScope.launch {
            _isLoadingComments.value = true
            _comments.value = getAllCommentUseCase(postId)
            _isLoadingComments.value = false
        }

    }

    fun deletePost(postId: String) {
        viewModelScope.launch {
            deletePostUseCase(postId)
        }
    }


    private val gson = Gson()

    fun commentPost(postId: String, parentId: String?, content: String) {
        viewModelScope.launch {
            commentUserCase(postId, parentId, content)
        }
    }

    fun start(postId: String?) {
        viewModelScope.launch {
            postRepository.connectSocket(postId) { data ->
                //Log.d("VM", "bla: $postId")
                val comment = gson.fromJson(
                    data.toString(),
                    CommentResponse::class.java
                )

                val newList = _comments.value.toMutableList()
                newList.add(comment.toComment())

                _comments.value = newList
                Log.d("VM", _comments.value.size.toString())
            }
        }
    }

    fun getFriends() {
        viewModelScope.launch {
            _friends.value = getFriendsUseCase()
        }
    }

    fun undoDeletePost(postId: String) {
        viewModelScope.launch {
            undoDeletePostUseCase(postId)
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        commentRepository.disconnectSocket()
//    }

}