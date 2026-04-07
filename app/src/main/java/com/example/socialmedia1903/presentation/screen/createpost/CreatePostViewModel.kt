package com.example.socialmedia1903.presentation.screen.createpost

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.source.LocalDataSource
import com.example.socialmedia1903.domain.usecase.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _isSaveToRoom = MutableStateFlow(true)
    val isSaveToRoom: StateFlow<Boolean> = _isSaveToRoom

    private val _isSavePost = MutableStateFlow(false)
    val isSavePost: StateFlow<Boolean> = _isSavePost

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


    fun createPost(
        postId: String,
        content: String,
        type: String,
        groupId: String?,
        contentType: String,
        anonymous: Boolean,
        visibility: String,
        context: Context
    ){
        viewModelScope.launch {
            _isSavePost.value = false
            val result = createPostUseCase(
                postId = postId,
                content = content,
                type = type,
                groupId = groupId,
                contentType = contentType,
                anonymous = anonymous,
                visibility = visibility
            )
            //_isSavePost.value = result
            if(result){

                val isDone =  createPostUseCase.uploadFile(context, postId)
                if(isDone){
                    clearAllImages()
                    _isSavePost.value = true
                }

            }else{
                _isSavePost.value = false
            }

        }
    }
}