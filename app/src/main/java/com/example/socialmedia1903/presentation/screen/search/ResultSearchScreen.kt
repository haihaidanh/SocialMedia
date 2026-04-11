package com.example.socialmedia1903.presentation.screen.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.presentation.screen.dashboard.post.PostItem

@Composable
fun ResultSearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    query: String
) {
    BackHandler {
        navController.navigate("home"){
            popUpTo(0)
        }
    }

    val users = viewModel.users.collectAsState().value
    val posts = viewModel.posts.collectAsState().value
    val scroll = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.onQueryChange(query)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Text(
                text = "Kết quả",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scroll)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (users.isNotEmpty()) {
                Text(
                    text = "Mọi người",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(users) { user ->
                        searchUserItem(user)
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

                posts.forEach { post ->
                    PostItem(
                        post,
                        navController = navController
                    )
                }

            }
        }
    }
}