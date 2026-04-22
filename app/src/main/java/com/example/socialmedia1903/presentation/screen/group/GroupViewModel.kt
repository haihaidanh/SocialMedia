package com.example.socialmedia1903.presentation.screen.group

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.domain.model.Friendship
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.usecase.AllGroupUseCase
import com.example.socialmedia1903.domain.usecase.CreateGroupUseCase
import com.example.socialmedia1903.domain.usecase.GetFriendsUseCase
import com.example.socialmedia1903.domain.usecase.GetGroupDetailUseCase
import com.example.socialmedia1903.domain.usecase.LeaveGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase,
    private val allGroupUseCase: AllGroupUseCase,
    private val getGroupDetailUseCase: GetGroupDetailUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val leaveGroupUseCase: LeaveGroupUseCase
) : ViewModel() {

    private val _isDone = MutableStateFlow(false)
    val isDone: StateFlow<Boolean> = _isDone

    private val _groups = MutableStateFlow<List<GroupInfo>>(emptyList())
    val groups: StateFlow<List<GroupInfo>> = _groups

    private val _group = MutableStateFlow(GroupInfo())
    val group: StateFlow<GroupInfo> = _group

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _friends = MutableStateFlow<List<Friendship>>(emptyList())
    val friends: StateFlow<List<Friendship>> = _friends

    fun createGroup(
        name: String,
        status: String,
        context: Context,
        avatarUri: Uri
    ) {
        viewModelScope.launch {
            val result = createGroupUseCase(name, status, context, avatarUri)
            _isDone.value = result
        }
    }

    fun getAllGroup() {
        viewModelScope.launch {
            val result = allGroupUseCase()
            _groups.value = result
        }
    }

    fun getGroupDetail(groupId: String) {
        viewModelScope.launch {
            val result = getGroupDetailUseCase(groupId)
            _group.value = result.groupInfo
            _posts.value = result.posts
        }
    }

    fun getFriends(){
        viewModelScope.launch {
            val result = getFriendsUseCase()
            _friends.value = result
        }
    }

    fun leaveGroup(
        groupId: String?
    ){
        viewModelScope.launch {
            leaveGroupUseCase(groupId)
        }
    }

}