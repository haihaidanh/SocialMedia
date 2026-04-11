package com.example.socialmedia1903.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.enums.InvitationType
import com.example.socialmedia1903.domain.usecase.InvitationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val invitationUseCase: InvitationUseCase,
    private val remoteDataSource: RemoteDataSource
): ViewModel(){
    fun addFriend(
        friendId: String?,
    ){
        viewModelScope.launch {
            invitationUseCase("add_friend", friendId, null)
        }
    }

    fun inviteGroup(
        groupId: String?,
        friendId: String?
    ){
        viewModelScope.launch {
            invitationUseCase("invite_group", friendId, groupId)
        }
    }

    fun acceptInvitation(
        type: InvitationType,
        groupId: String? = null,
        userId: String
    ){
        viewModelScope.launch {
            remoteDataSource.acceptInvitation(type, groupId, userId)
        }
    }

    fun rejectInvitation(
        type: InvitationType,
        groupId: String? = null,
        userId: String
    ){
        viewModelScope.launch {
            remoteDataSource.rejectInvitation(type, groupId, userId)
        }
    }
}