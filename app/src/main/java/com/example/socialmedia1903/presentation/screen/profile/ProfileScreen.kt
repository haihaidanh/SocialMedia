package com.example.socialmedia1903.presentation.screen.profile

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.socialmedia1903.data.utils.InvitationStatus


@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    id: String
) {

    BackHandler {
        navController.navigate("home") {
            popUpTo(0)
        }
    }

    LaunchedEffect(Unit) {
        profileViewModel.getProfile(id)
    }
    val status by profileViewModel.status.collectAsState()
    val profile by profileViewModel.profile.collectAsState()
    val friends by profileViewModel.friends.collectAsState()
    var showUnfriendDialog by remember { mutableStateOf(false) }

    UnfriendBottomSheet(
        showUnfriendDialog,
        onDismiss = {
            showUnfriendDialog = false
        },
        onUnfriend = {
            profileViewModel.unFriend(id)
            profileViewModel.setStatus(InvitationStatus.NONE)
            showUnfriendDialog = false
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {

            // Ảnh bìa
            Box {

                AsyncImage(
                    model = profile.background,
                    contentDescription = "Cover",
                    placeholder = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                    error = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                AsyncImage(
                    model = profile.avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.White, CircleShape)
                        .align(Alignment.BottomStart)
                        .offset(x = 16.dp, y = 50.dp)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Tên
            Text(
                text = profile.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Spacer(modifier = Modifier.height(12.dp))

            // Thông tin cá nhân
            Text(
                text = "Thông tin cá nhân",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Spacer(modifier = Modifier.height(12.dp))

            // Bạn bè
            Text(
                text = "Bạn bè",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
