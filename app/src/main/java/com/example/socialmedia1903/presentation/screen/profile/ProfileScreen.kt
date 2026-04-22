package com.example.socialmedia1903.presentation.screen.profile

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.domain.enums.InvitationStatus
import com.example.socialmedia1903.domain.enums.InvitationType
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel
import com.example.socialmedia1903.presentation.screen.dashboard.post.PostItem
import com.example.socialmedia1903.presentation.screen.dashboard.post.PostItemView


@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    invitationViewModel: InvitationViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    id: String
) {

    BackHandler {
        navController.navigate("home") {
            popUpTo(0)
        }
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getUserId()
    }

    val userId by dashboardViewModel.userId.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.getProfile(id)
    }
    val status by profileViewModel.status.collectAsState()
    val profile by profileViewModel.profile.collectAsState()
    val posts by profileViewModel.posts.collectAsState()
    val friends by profileViewModel.friends.collectAsState()
    var showUnfriendDialog by remember { mutableStateOf(false) }
    var showResponseDialog by remember { mutableStateOf(false) }
    val scroll = rememberScrollState()

    ResponseAddRequest(
        showResponseDialog,
        onDismiss = {
            showResponseDialog = false
        },
        onAccept = {
            invitationViewModel.acceptInvitation(
                InvitationType.ADD_FRIEND, userId = profile.userId
            )
            showResponseDialog = false
        },
        onDecline = {
            invitationViewModel.rejectInvitation(
                InvitationType.ADD_FRIEND, userId = profile.userId
            )
            showResponseDialog = false
        }
    )

    BottomSheet(
        showUnfriendDialog,
        content = listOf(
            BottomSheetItem("Hủy kết bạn", onClick = {
                profileViewModel.unFriend(id)
                profileViewModel.setStatus(InvitationStatus.NONE)
                showUnfriendDialog = false
            })
        ),
        onDismiss = {
            showUnfriendDialog = false
        },
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = profile.background ?: R.drawable.background,
                    contentDescription = "Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.LightGray)
                        .align(Alignment.TopCenter)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = (-10).dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.White)
                )
                AsyncImage(
                    model = profile.avatarUrl,
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 30.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .border(3.dp, Color.LightGray, CircleShape)
                        .align(Alignment.BottomStart)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = profile.username,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )

            Text(
                text = "@" + profile.name,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp)
            )

            if (status == InvitationStatus.ACCEPTED) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Button(
                        onClick = {
                            showUnfriendDialog = true
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(0.5f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Bạn bè")
                    }
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(0.5f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Nhắn tin")
                    }
                }

            } else if (status == InvitationStatus.PENDING) {
                Button(
                    onClick = {
                        profileViewModel.setStatus(InvitationStatus.NONE)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Đã gửi lời mời kết bạn")
                }
            } else if (status == InvitationStatus.INVITED) {
                Button(
                    onClick = {
                        showResponseDialog = true
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Phản hồi")
                }
            } else {
                Button(
                    onClick = {
                        profileViewModel.setStatus(InvitationStatus.PENDING)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Kết bạn")
                }
            }

            TextAndIcon(
                text = "Mô tả",
                icon = R.drawable.edit,
                onIconClick = {

                },
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = "",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(10.dp)
            )

            TextAndIcon(
                text = "Thông tin cá nhân",
                icon = R.drawable.edit,
                onIconClick = {

                }
            )
            FriendsPreview(
                friends = friends,
                onSeeAllClick = {

                },
                navController,
                modifier = Modifier
                    .padding(10.dp)
            )
            Log.d("hai", "posts: ${posts.size}")
            posts.forEach { post ->
                userId?.let { userId ->
                    PostItemView(
                        post = post,
                        navController = navController,
                        userId = userId
                    )
                }
            }

        }
    }
}
