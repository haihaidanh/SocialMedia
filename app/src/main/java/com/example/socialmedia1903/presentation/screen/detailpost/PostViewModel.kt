package com.example.socialmedia1903.presentation.screen.detailpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.mapper.HaiMapper.toComment
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

data class PendingDelete(
    val post: Post,
    val job: Job
)

data class ShowReactionState(
    val show: Boolean = false,
    val postId: String? = null
)

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
    private val pendingDeletes = mutableMapOf<String, PendingDelete>()

    private val _like = MutableStateFlow(Like())
    val like: StateFlow<Like> = _like

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    private val _showReaction = MutableStateFlow(ShowReactionState())
    val showReaction: StateFlow<ShowReactionState> = _showReaction


    private val _friends = MutableStateFlow<List<Friendship>>(emptyList())
    val friends: StateFlow<List<Friendship>> = _friends

    private val _undoEvent = MutableSharedFlow<Post>()
    val undoEvent = _undoEvent.asSharedFlow()

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

    fun resetPost() {
        _post.value = null
    }


    fun getAllComment(postId: String) {
        viewModelScope.launch {
            _comments.value = getAllCommentUseCase(postId)
        }

    }

    fun deletePost(post: Post) {

        val job = viewModelScope.launch {
            delay(5000.milliseconds)
            deletePostUseCase(post.id)
            pendingDeletes.remove(post.id)
        }

        pendingDeletes[post.id] = PendingDelete(post, job)

        viewModelScope.launch {
            _undoEvent.emit(post)
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
            }
        }
    }

    fun getFriends() {
        viewModelScope.launch {
            _friends.value = getFriendsUseCase()
        }
    }

    fun undoDelete(post: Post) {
        val pending = pendingDeletes[post.id] ?: return
        pending.job.cancel()
        pendingDeletes.remove(post.id)

        viewModelScope.launch {
            undoDeletePostUseCase(post.id)
        }
    }

    fun setShowReactionState(show: Boolean, postId: String? = null) {
        _showReaction.value = ShowReactionState(show, postId)
    }

//    override fun onCleared() {
//        super.onCleared()
//        commentRepository.disconnectSocket()
//    }

}