package com.example.socialmedia1903.domain.repository

import android.content.Context
import android.net.Uri
import com.example.socialmedia1903.domain.model.Group
import com.example.socialmedia1903.domain.model.GroupInfo

interface GroupRepository {
    suspend fun createGroup(
        name: String,
        status: String,
        avatarUri: Uri
    ) : Boolean
    suspend fun getAllGroups(): List<GroupInfo>
    suspend fun deleteGroup(groupId: String?)
    suspend fun addMemberToGroup(groupId: String?, userId: String?)
    suspend fun removeMemberFromGroup(groupId: String?, userId: String?)
    suspend fun getGroupDetails(groupId: String) : Group
    suspend fun leaveGroup(groupId: String?)
}