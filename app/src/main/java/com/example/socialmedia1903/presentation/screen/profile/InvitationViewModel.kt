package com.example.socialmedia1903.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.domain.enums.InvitationType
import com.example.socialmedia1903.domain.usecase.AcceptInvitationUseCase
import com.example.socialmedia1903.domain.usecase.RejectInvitationUseCase
import com.example.socialmedia1903.domain.usecase.SendInvitationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val sendInvitationUseCase: SendInvitationUseCase,
    private val acceptInvitationUseCase: AcceptInvitationUseCase,
    private val rejectInvitationUseCase: RejectInvitationUseCase,
): ViewModel(){
    fun addFriend(
        friendId: String?,
    ){
        viewModelScope.launch {
            sendInvitationUseCase("add_friend", friendId, null)
        }
    }

    fun inviteGroup(
        groupId: String?,
        friendId: String?
    ){
        viewModelScope.launch {
            sendInvitationUseCase("invite_group", friendId, groupId)
        }
    }

    fun acceptInvitation(
        type: InvitationType,
        groupId: String? = null,
        userId: String
    ){
        viewModelScope.launch {
            acceptInvitationUseCase(type, groupId, userId)
        }
    }

    fun rejectInvitation(
        type: InvitationType,
        groupId: String? = null,
        userId: String
    ){
        viewModelScope.launch {
            rejectInvitationUseCase(type, groupId, userId)
        }
    }
}