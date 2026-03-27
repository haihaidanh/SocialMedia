package com.example.socialmedia1903.presentation.screen.dashboard

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.socialmedia1903.data.dto.response.PostResponse
import kotlinx.coroutines.launch

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController,
    padding: PaddingValues
) {
    val token by dashboardViewModel.token.collectAsState()
    val checkLogIn by dashboardViewModel.checkLogIn.collectAsState()

    LaunchedEffect(Unit) {
        dashboardViewModel.getToken()
    }

    LaunchedEffect(token, checkLogIn) {
        if (!checkLogIn && token == null) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getAvatar()
    }

    val posts: LazyPagingItems<PostResponse> = dashboardViewModel.posts.collectAsLazyPagingItems()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val avatar by dashboardViewModel.avatar.collectAsState()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState, dashboardViewModel)
        }
    ) {
        Scaffold(
            topBar = {
                Header(
                    drawerOnClick = { scope.launch { drawerState.open() } },
                    searchOnClick = { /* TODO */ }
                )
            },
            bottomBar = {
                CustomBottomBarWithFab(navController)
            },
            modifier = Modifier.padding(padding)
        ) { padding ->

            // 🔹 SWIPE REFRESH
            val isRefreshing = posts.loadState.refresh is androidx.paging.LoadState.Loading
            val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { posts.refresh() } // 🔹 đây là quan trọng
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {

                    item{
                        createPostScreen(
                            onCreatePost = { navController.navigate("create_post") },
                            modifier = Modifier
                                .padding(5.dp),
                            avatar = avatar?:""
                        )
                    }

                    items(count = posts.itemCount) { index ->
                        val post = posts[index]
                        post?.let { PostItem(it, navController= navController) }
                    }

                    // Load state thêm
                    posts.apply {
                        when {
                            loadState.append is androidx.paging.LoadState.Loading -> {
                                item { Text("Đang tải thêm...") }
                            }
                            loadState.refresh is androidx.paging.LoadState.Error -> {
                                val e = loadState.refresh as androidx.paging.LoadState.Error
                                item { Text("Lỗi: ${e.error.localizedMessage}") }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun createPostScreen(
    onCreatePost: () -> Unit,
    modifier: Modifier,
    avatar: String
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onCreatePost()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = avatar,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp)
                .clip(CircleShape)
        )
        Text(text = "What are you thinking?..")
    }
}