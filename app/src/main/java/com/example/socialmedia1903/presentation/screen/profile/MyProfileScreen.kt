package com.example.socialmedia1903.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.data.dto.response.UserResponse
import com.example.socialmedia1903.presentation.screen.dashboard.CustomBottomBarWithFab


@Composable
fun MyProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    padding: PaddingValues
) {
    LaunchedEffect(Unit) {
        profileViewModel.getMyProfile()
    }

    val scroll = rememberScrollState()
    val profile by profileViewModel.profile.collectAsState()
    val friends by profileViewModel.friends.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // Ảnh bìa
            Box {
                AsyncImage(
                    model = profile.background,
                    contentDescription = "Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.Red)
                )

                // Avatar (đè lên ảnh bìa)
                AsyncImage(
                    model = profile.avatarUrl,
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.White, CircleShape)
                        .align(Alignment.BottomStart)
                        .offset(x = 16.dp, y = 70.dp)
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

            Text(
                text = "Mô tả",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Thông tin cá nhân",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(12.dp))

            FriendsPreview(
                friends = friends,
                onSeeAllClick = {

                },
                navController
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            CustomBottomBarWithFab(navController) {

            }
        }


    }
}

@Composable
fun FriendsPreview(
    friends: List<UserResponse>,
    onSeeAllClick: () -> Unit,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Title + nút xem tất cả
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Bạn bè",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Xem tất cả",
                color = Color.Blue,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        // Grid 2 hàng 3 cột
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(220.dp),
            userScrollEnabled = false
        ) {
            items(friends.take(6)) { friend ->
                FriendItem(
                    friend,
                    onClick = {
                        navController.navigate("profile/${friend.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun FriendItem(
    friend: UserResponse,
    onClick: () -> Unit,

) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = friend.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = friend.name,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}

