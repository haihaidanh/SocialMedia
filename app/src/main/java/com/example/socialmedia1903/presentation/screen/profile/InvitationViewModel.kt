package com.example.socialmedia1903.presentation.screen.profile

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.domain.usecase.InvitationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val invitationUseCase: InvitationUseCase
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
}