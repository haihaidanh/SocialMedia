package com.example.socialmedia1903.presentation.screen.story

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.story.StoryResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): ViewModel(){

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted

    private val _stories = MutableStateFlow<List<StoryResponse>>(emptyList())
    val stories: StateFlow<List<StoryResponse>> = _stories

    fun createStory(uri: Uri, context: Context){
        viewModelScope.launch {
            remoteDataSource.createStory(uri, context)
            _isCompleted.value = true
        }
    }

    fun getStories(){
        viewModelScope.launch {
           _stories.value = remoteDataSource.getStories()
        }
    }
}