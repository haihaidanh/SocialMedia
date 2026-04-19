package com.example.socialmedia1903.presentation.screen.notification

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.domain.enums.InvitationType
import com.example.socialmedia1903.domain.enums.NotificationType
import com.example.socialmedia1903.presentation.screen.dashboard.CustomBottomBarWithFab
import com.example.socialmedia1903.presentation.screen.profile.InvitationViewModel

@Composable
fun NotificationScreen(
    navController: NavController,
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    invitationViewModel: InvitationViewModel = hiltViewModel(),
    padding: PaddingValues
) {

    val notifications by notificationViewModel.notifications.collectAsState()

    LaunchedEffect(Unit) {
        notificationViewModel.getNotifications()
    }

    Scaffold (
        bottomBar = {
            CustomBottomBarWithFab(
                navController,
                onClick = {

                }
            )
        },
        modifier = Modifier.padding(padding)
    ){ paddingValues ->
        if(notifications.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No notifications")
            }
        }else{
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(notifications) {notification ->
                    Log.d("hai", notification.toString())
                    when(notification.type){
                        NotificationType.ADD_FRIEND -> {
                            AddFriendItem(
                                notification,
                                onAccept = {
                                    invitationViewModel.acceptInvitation(InvitationType.ADD_FRIEND, userId = notification.userId)
                                },
                                onReject = {
                                    invitationViewModel.rejectInvitation(InvitationType.ADD_FRIEND, userId = notification.userId)
                                },
                                onItemClick = {
                                    navController.navigate("profile/${notification.userId}")
                                }

                            )
                        }

                        NotificationType.LIKE -> {
                            LikePostItem(
                                notification,
                                onClick = {
                                    navController.navigate("detail/${notification.resourceId}")
                                }
                            )
                        }
                        NotificationType.COMMENT -> {
                            CommentPostItem(
                                notification,
                                onClick = {
                                    navController.navigate("detail/${notification.resourceId}")
                                }
                            )
                        }
                        NotificationType.SHARE -> {
                            SharePostItem(
                                notification,
                                onClick = {
                                    navController.navigate("detail/${notification.resourceId}")
                                }
                            )
                        }
                        NotificationType.FOLLOW -> TODO()
                        NotificationType.INVITE_GROUP -> {
                            InviteGroupItem(
                                notification.groupAvatar ?: "",
                                notification.user.avatarUrl,
                                notification.groupName ?: "",
                                onAccept = {
                                    Log.d("hai", notification.groupId ?: "")
                                    invitationViewModel.acceptInvitation(InvitationType.INVITE_GROUP, notification.groupId, notification.userId)
                                },
                                onView = {
                                    navController.navigate("group/${notification.groupId}")
                                }
                            )

                        }
                        NotificationType.ACCEPT -> {

                        }
                        NotificationType.REJECT -> TODO()
                    }
                }
        }

    }


    }
}
