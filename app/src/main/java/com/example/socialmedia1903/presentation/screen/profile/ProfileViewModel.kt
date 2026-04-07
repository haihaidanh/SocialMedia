package com.example.socialmedia1903.presentation.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import com.example.socialmedia1903.data.dto.response.Profile
import com.example.socialmedia1903.data.dto.response.UserResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.data.utils.InvitationStatus
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
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile

    private val _friends = MutableStateFlow<List<UserResponse>>(emptyList())
    val friends: StateFlow<List<UserResponse>> = _friends

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
        }
    }

    fun getMyProfile(){
        viewModelScope.launch {
            val result = profileUseCase.getMyProfile()
            _profile.value = result.profile
            _friends.value = result.friends
        }
    }

    fun unFriend(friendId: String){
        viewModelScope.launch {
            remoteDataSource.unFriend(friendId)
        }
    }
}