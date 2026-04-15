package com.example.socialmedia1903.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.ProfileInfoResponse
import com.example.socialmedia1903.data.dto.response.UserResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.enums.InvitationStatus
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.model.ProfileInfo
import com.example.socialmedia1903.domain.model.User
import com.example.socialmedia1903.domain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: GetProfileUseCase,
    private val remoteDataSource: RemoteDataSource
): ViewModel() {
    private val _profile = MutableStateFlow(ProfileInfo())
    val profile: StateFlow<ProfileInfo> = _profile

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _friends = MutableStateFlow<List<User>>(emptyList())
    val friends: StateFlow<List<User>> = _friends

    private val _status = MutableStateFlow(InvitationStatus.NONE)
    val status: StateFlow<InvitationStatus> = _status

    fun setStatus(
        status: InvitationStatus
    ){
        _status.value = status
    }

    fun getProfile(id: String){
        viewModelScope.launch {
            val result = profileUseCase(id)
            _profile.value = result.profile
            _status.value = result.status
            _friends.value = result.friends
            _posts.value = result.posts
        }
    }

    fun getMyProfile(){
        viewModelScope.launch {
            val result = profileUseCase.getMyProfile()
            _profile.value = result.profile
            _friends.value = result.friends
            _posts.value = result.posts
        }
    }

    fun unFriend(friendId: String){
        viewModelScope.launch {
            remoteDataSource.unFriend(friendId)
        }
    }
}