package com.example.socialmedia1903.presentation.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.NotificationResponse
import com.example.socialmedia1903.data.dto.response.Profile
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.data.utils.NotificationType
import com.example.socialmedia1903.domain.usecase.GetNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val remoteDataSource: RemoteDataSource
): ViewModel(){
    private val _notifications = MutableStateFlow<List<NotificationResponse>>(emptyList())
    val notifications: StateFlow<List<NotificationResponse>> = _notifications

    fun getNotifications(){
        viewModelScope.launch {
            val result = getNotificationsUseCase()
            _notifications.value = result
        }
    }

    fun acceptInvitation(
        type: NotificationType,
        groupId: String? = null
    ){
        viewModelScope.launch {
            remoteDataSource.acceptInvitation(type, groupId)
        }
    }
}