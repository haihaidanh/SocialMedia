package com.example.socialmedia1903.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.dto.response.SearchItemResponse
import com.example.socialmedia1903.data.dto.response.UserResponse
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.model.User
import com.example.socialmedia1903.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): ViewModel(){
    private val _users = MutableStateFlow<List<SearchItemResponse>>(emptyList())
    val users: StateFlow<List<SearchItemResponse>> = _users

    private val _posts = MutableStateFlow<List<PostResponse>>(emptyList())
    val posts: StateFlow<List<PostResponse>> = _posts

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text

    fun onQueryChange(query: String){
        viewModelScope.launch {
            _text.value = query
            if (query.isNotEmpty()) {
                searchUsers(query)
            } else {
                _users.value = emptyList()
            }
        }
    }

    private fun searchUsers(query: String) {
        viewModelScope.launch {
            try {
                val result = searchUseCase(query)
                _users.value = result.users
                _posts.value = result.posts
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}