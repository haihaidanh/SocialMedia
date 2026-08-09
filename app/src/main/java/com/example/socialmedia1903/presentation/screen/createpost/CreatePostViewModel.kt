package com.example.socialmedia1903.presentation.screen.createpost

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.socialmedia1903.data.source.LocalDataSource
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.usecase.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _isSaveToRoom = MutableStateFlow(true)
    val isSaveToRoom: StateFlow<Boolean> = _isSaveToRoom

    private val _loadingSave = MutableStateFlow(false)
    val loadingSave: StateFlow<Boolean> = _loadingSave

    fun saveImageUri(uri: String) {
        viewModelScope.launch {
            _isSaveToRoom.value = false
            localDataSource.insertImage(uri)
            _isSaveToRoom.value = true
        }
    }

    fun clearAllImages() {
        viewModelScope.launch {
            localDataSource.deleteAllImages()
        }
    }

    fun setLoadingSave(value: Boolean) {
        _loadingSave.value = value
    }

    fun createPost(
        postId: String,
        content: String,
        type: PostType,
        groupId: String?,
        contentType: String,
        anonymous: Boolean,
        visibility: PostVisibility,
        background: String?,
        context: Context
    ) {
        val post = Post(
            id = postId,
            content = content,
            type = type,
            groupId = groupId,
            contentType = contentType,
            anonymous = anonymous,
            visibility = visibility,
            background = background
        )

        Log.d("hai", "Creating post: $post")

        viewModelScope.launch {
            val workId = createPostUseCase(post)
            WorkManager.getInstance(context)
                .getWorkInfoByIdLiveData(workId).observeForever { workInfo ->
                    if (workInfo != null) {
                        if (workInfo.state.isFinished) {
                            _loadingSave.value = false
                        }
                    }
                }
        }
    }
}