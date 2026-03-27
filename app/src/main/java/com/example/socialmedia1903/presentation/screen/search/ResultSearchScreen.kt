package com.example.socialmedia1903.presentation.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.presentation.screen.dashboard.PostItem

@Composable
fun ResultSearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val users = viewModel.users.collectAsState().value
    val posts = viewModel.posts.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔹 Title
        Text(
            text = "Kết quả",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Section: Mọi người
        if (users.isNotEmpty()) {
            Text(
                text = "Mọi người",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.heightIn(max = 200.dp) // giới hạn chiều cao nếu muốn
            ) {
                items(users) { user ->
                    UserItem(user)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 🔹 Section: Bài viết
        if (posts.isNotEmpty()) {
            Text(
                text = "Bài viết",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

//            LazyColumn {
//                items(posts) { post ->
//                    PostItem(
//                        post
//                    )
//                }
//            }
        }
    }
}