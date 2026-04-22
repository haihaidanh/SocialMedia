package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.data.source.PostPagingSource
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val apiService: AppService,
) : ViewModel() {

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    private val _name = MutableStateFlow<String?>(null)
    val name: StateFlow<String?> = _name

    private val _avatar = MutableStateFlow<String?>(null)
    val avatar: StateFlow<String?> = _avatar

    private val _checkLogIn = MutableStateFlow(true)
    val checkLogIn: StateFlow<Boolean> = _checkLogIn

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username


    val posts: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(
            pageSize = 2,
            initialLoadSize = 2,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PostPagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)

    fun logOut() {
        viewModelScope.launch {
            userRepository.logout()
            _checkLogIn.value = true
        }
    }


    fun getUserName() {
        viewModelScope.launch {
            _username.value = userRepository.getUsername()
        }
    }

    fun getAvatar() {
        viewModelScope.launch {
            _avatar.value = userRepository.getAvatarUrl()
        }
    }

    fun getUserId() {
        viewModelScope.launch {
            _userId.value = userRepository.getUserId()
        }
    }


}