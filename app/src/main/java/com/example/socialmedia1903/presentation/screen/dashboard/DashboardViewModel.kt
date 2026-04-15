package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.data.local.MyPreference
import com.example.socialmedia1903.data.source.PostPagingSource
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val apiService: AppService,
    private val myPreference: MyPreference,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

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
            myPreference.clear()
            remoteDataSource.logOut()
            _checkLogIn.value = true
        }
    }

    fun getToken() {
        viewModelScope.launch {
            _token.value = myPreference.getAccessToken()
            _checkLogIn.value = false
        }
    }

    fun getName() {
        viewModelScope.launch {
            _name.value = myPreference.getName()
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            _username.value = myPreference.getUsername()
        }
    }

    fun getAvatar() {
        viewModelScope.launch {
            _avatar.value = myPreference.getAvatarUrl()
        }
    }

}