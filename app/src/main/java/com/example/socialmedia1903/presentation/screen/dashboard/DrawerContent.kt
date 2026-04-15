package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun DrawerItem(
    title: String,
    onClick: () -> Unit
) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    )
}

@Composable
fun DrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    viewmodel: DashboardViewModel
) {

    LaunchedEffect(Unit) {
        viewmodel.getAvatar()
    }

    LaunchedEffect(Unit) {
        viewmodel.getUserName()
    }

    val scope = rememberCoroutineScope()
    val avatar by viewmodel.avatar.collectAsState()
    val username by viewmodel.username.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(30.dp))
    ) {

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = avatar,
                contentDescription = "hai",
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .size(30.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = username ?: "hai",
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {


            Divider()

            // 🔹 Item
            DrawerItem("Tạo nhóm") {
                navController.navigate("create-group")
                scope.launch { drawerState.close() }
            }

            DrawerItem("Nhóm của tôi") {
                navController.navigate("my-group")
                scope.launch { drawerState.close() }
            }

            DrawerItem("Cài đặt") {
                navController.navigate("setting")
                scope.launch { drawerState.close() }
            }

            DrawerItem("Logout") {
                navController.navigate("login") {
                    viewmodel.logOut()
                    scope.launch { drawerState.close() }
                    popUpTo(0)
                }
            }
        }

    }
}
