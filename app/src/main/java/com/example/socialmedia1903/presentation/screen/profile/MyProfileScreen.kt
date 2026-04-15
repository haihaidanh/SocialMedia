package com.example.socialmedia1903.presentation.screen.profile

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.socialmedia1903.data.dto.response.UserResponse
import com.example.socialmedia1903.domain.model.User
import com.example.socialmedia1903.presentation.screen.dashboard.CustomBottomBarWithFab
import com.example.socialmedia1903.presentation.screen.dashboard.post.PostItem


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
    val posts by profileViewModel.posts.collectAsState()
    val friends by profileViewModel.friends.collectAsState()
    var selectedIndex by remember { mutableStateOf(0) }
    var isEditInfo by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding)
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


            val tabs = listOf("Tất cả", "Thông tin", "Ảnh")

            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                tabs.forEachIndexed { index, title ->

                    val isSelected = selectedIndex == index

                    Button(
                        onClick = { selectedIndex = index },
                        modifier = Modifier,
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color(0xFFd10058).copy(alpha = 0.8f) else Color.White,
                            contentColor = if (isSelected) Color.White else Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text(text = title)
                    }
                }
            }

            when (selectedIndex) {
                0 -> {
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
                        PostItem(
                            post = post,
                            navController = navController
                        )
                    }
                }

                1 -> {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Thông tin cá nhân",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    isEditInfo = !isEditInfo
                                }
                        )
                    }


                    EditableTextComponent(
                        edit = isEditInfo,
                        text = "Phone Number",
                        content = profile.phoneNumber ?: "",
                        onSave = {

                        }
                    )
                    EditableTextComponent(
                        edit = isEditInfo,
                        text = "Address",
                        content = profile.birthday ?: "",
                        onSave = {}
                    )

                    EditableTextComponent(
                        edit = isEditInfo,
                        text = "Birthday",
                        content = profile.birthday ?: "",
                        onSave = {}
                    )

                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Lưu")
                    }
                }

                2 -> {
                    Text(
                        text = "Ảnh",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

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
    friends: List<User>,
    onSeeAllClick: () -> Unit,
    navController: NavController,
    modifier: Modifier
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp),
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
fun EditableTextComponent(
    edit: Boolean = false,
    text: String,
    content: String,
    onSave: (String) -> Unit
) {
    var ct by remember { mutableStateOf(content) }

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )


        if (edit) {
            TextField(
                value = ct,
                onValueChange = { ct = it },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = ct,
                fontSize = 16.sp
            )
            onSave(ct)
        }
    }
}

@Composable
fun TextAndIcon(
    text: String,
    icon: Int,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .clickable { onIconClick() }
        )
    }
}

@Composable
fun FriendItem(
    friend: User,
    onClick: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = friend.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = friend.name,
            fontSize = 16.sp,
            maxLines = 1
        )
    }
}

