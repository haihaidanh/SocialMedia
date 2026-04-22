package com.example.socialmedia1903.data.repository

import android.content.Context
import android.net.Uri
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Group
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.domain.repository.GroupRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @ApplicationContext private val context: Context
) : GroupRepository {
    override suspend fun createGroup(
        name: String,
        status: String,
        avatarUri: Uri,
    ): Boolean {
        return remoteDataSource.createGroup(name, status, avatarUri, context)
    }

    override suspend fun getAllGroups(): List<GroupInfo> {
        return remoteDataSource.getGroups()
    }

    override suspend fun deleteGroup(
        groupId: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun addMemberToGroup(groupId: String?, userId: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun removeMemberFromGroup(groupId: String?, userId: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupDetails(groupId: String) : Group{
        return remoteDataSource.getGroupDetail(groupId)
    }

    override suspend fun leaveGroup(groupId: String?) {
        remoteDataSource.leaveGroup(groupId)
    }
}