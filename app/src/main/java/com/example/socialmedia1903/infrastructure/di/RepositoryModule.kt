package com.example.socialmedia1903.infrastructure.di

import com.example.socialmedia1903.data.repository.FriendRepositoryImpl
import com.example.socialmedia1903.data.repository.GroupRepositoryImpl
import com.example.socialmedia1903.data.repository.InvitationRepositoryImpl
import com.example.socialmedia1903.data.repository.PostRepositoryImpl
import com.example.socialmedia1903.data.repository.SettingLanguageRepositoryImpl
import com.example.socialmedia1903.data.repository.SettingThemeRepositoryImpl
import com.example.socialmedia1903.data.repository.UserRepositoryImpl
import com.example.socialmedia1903.domain.repository.FriendRepository
import com.example.socialmedia1903.domain.repository.GroupRepository
import com.example.socialmedia1903.domain.repository.InvitationRepository
import com.example.socialmedia1903.domain.repository.PostRepository
import com.example.socialmedia1903.domain.repository.SettingLanguageRepository
import com.example.socialmedia1903.domain.repository.SettingThemeRepository
import com.example.socialmedia1903.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    abstract fun bindSettingThemeRepository(settingThemeRepositoryImpl: SettingThemeRepositoryImpl): SettingThemeRepository

    @Binds
    abstract fun bindSettingLanguageRepository(settingLanguageRepositoryImpl: SettingLanguageRepositoryImpl): SettingLanguageRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindFriendRepository(friendRepositoryImpl: FriendRepositoryImpl): FriendRepository

    @Binds
    abstract fun bindGroupRepository(groupRepositoryImpl: GroupRepositoryImpl): GroupRepository

    @Binds
    abstract fun bindInvitationRepository(invitationRepositoryImpl: InvitationRepositoryImpl): InvitationRepository

}