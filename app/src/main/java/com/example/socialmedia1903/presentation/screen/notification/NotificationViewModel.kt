package com.example.socialmedia1903.presentation.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.NotificationResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.Notification
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
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    fun getNotifications(){
        viewModelScope.launch {
            val result = getNotificationsUseCase()
            _notifications.value = result
        }
    }

}