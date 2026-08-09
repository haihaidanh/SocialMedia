package com.example.socialmedia1903.presentation.screen.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.presentation.component.Header
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.core.post.PostItemView
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel

@Composable
fun ResultSearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    query: String
) {
    BackHandler {
        navController.navigate("home") {
            popUpTo(0)
        }
    }

    val users by viewModel.users.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val scroll = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.onQueryChange(query)
        dashboardViewModel.getUserId()
    }

    val userId by dashboardViewModel.userId.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .padding(horizontal = 10.dp)
    ) {
        Header(
            title = stringResource(R.string.result_search),
            onBackClick = {
                navController.navigate("home") {
                    popUpTo(0)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (users.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.people),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(users) { user ->
                        SearchUserItem(user)
                    }
                }
            }

            if (posts.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.posts),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = fontOpenSansBoldHelper(),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                posts.forEach { post ->
                    userId?.let {
                        PostItemView(
                            post,
                            userId = it,
                            onCommentClick = {
                                navController.navigate("post_detail/${post.id}")
                            },
                            modifier = Modifier
                                .padding(bottom = 4.dp),
                            onGroupClick = {

                            },
                            onPostClick = {

                            },
                            onUserClick = {

                            },
                            onLikePost = { postId, type ->

                            },
                            onShowReact = { postId, show ->

                            },
                            showReactions = false,
                        )
                    }
                }

            }
        }
    }
}