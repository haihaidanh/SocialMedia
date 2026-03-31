package com.example.socialmedia1903.presentation.screen.createpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.source.LocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
) : ViewModel() {
    
    private val _uris = MutableStateFlow<List<String>>(emptyList())
    val uris: StateFlow<List<String>> = _uris
    
    fun saveImageUri(uri: String) {
        viewModelScope.launch {
            val postId = UUID.randomUUID().toString()
            localDataSource.insertImage(uri, postId)
        }
    }

    fun getImageUrisForPost(postId: String){
        viewModelScope.launch {
            val uris = localDataSource.getImagesByPostId(postId).map { it.uri }
            _uris.value = uris
        }
    }

    fun clearAllImages() {
        viewModelScope.launch {
            localDataSource.deleteAllImages()
        }
        
    }
}