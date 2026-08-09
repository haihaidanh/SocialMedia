package com.example.socialmedia1903.presentation.screen.dashboard

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.core.post.PostItemView
import com.example.socialmedia1903.presentation.navigation.Screen
import com.example.socialmedia1903.presentation.screen.detailpost.PostViewModel
import com.example.socialmedia1903.presentation.screen.story.AddStoryItem
import com.example.socialmedia1903.presentation.screen.story.StoryItem
import com.example.socialmedia1903.presentation.screen.story.StoryViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    storyViewModel: StoryViewModel = hiltViewModel(),
    postViewModel: PostViewModel,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        storyViewModel.getStories()
        dashboardViewModel.getAvatar()
        dashboardViewModel.getUserName()
        dashboardViewModel.getUserId()
    }


    val stories by storyViewModel.stories.collectAsState()

    val posts: LazyPagingItems<Post> = dashboardViewModel.posts.collectAsLazyPagingItems()

    val showReaction by postViewModel.showReaction.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val avatar by dashboardViewModel.avatar.collectAsState()
    val username by dashboardViewModel.username.collectAsState()

    val userId by dashboardViewModel.userId.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                avatar = avatar ?: "",
                username = username ?: "",
                onCreateGroup = {
                    navController.navigate("create-group")
                    scope.launch {
                        drawerState.close()
                    }
                },
                onMyGroups = {
                    navController.navigate("my-groups")
                    scope.launch {
                        drawerState.close()
                    }
                },
                onSetting = {
                    navController.navigate("setting")
                    scope.launch {
                        drawerState.close()
                    }
                },
                onLogout = {
                    dashboardViewModel.logOut()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Header(
                    drawerOnClick = { scope.launch { drawerState.open() } },
                    searchOnClick = {
                        navController.navigate("search")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .staticStatusBarPadding()
                )
                val isRefreshing = posts.loadState.refresh is androidx.paging.LoadState.Loading
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { posts.refresh() }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {

                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(10.dp)
                                    )
                                    .background(
                                        color = MaterialTheme.colorScheme.surface
                                    )
                            ) {
                                item {
                                    AddStoryItem(
                                        avatarUrl = avatar ?: "",
                                        onAddClick = {
                                            navController.navigate("open-camera")
                                        },
                                        modifier = Modifier.padding(start = 10.dp)
                                    )
                                }
                                if (stories.isNotEmpty()) {
                                    items(stories) { story ->
                                        StoryItem(
                                            avatarUrl = story.user.avatarUrl,
                                            thumbnail = story.thumbnail,
                                            onClick = {
                                            },
                                            modifier = Modifier
                                        )
                                    }
                                }

                            }

                        }

                        item {
                            CreatePostItem(
                                onCreatePost = { navController.navigate(Screen.CreatePost.route) },
                                modifier = Modifier,
                                avatar = avatar ?: ""
                            )
                        }

                        items(
                            count = posts.itemCount,
                            key = posts.itemKey { it.id }
                        ) { index ->
                            val post = posts[index]
                            post?.let {
                                PostItemView(
                                    post = it,
                                    userId = userId ?: "",
                                    onCommentClick = {
                                        postViewModel.getDetailPost(it.id)
                                        navController.navigate(Screen.DetailPost.route)
                                    },
                                    showReactions = showReaction.show && showReaction.postId == it.id,
                                    onLikePost = { postId, type ->
                                        postViewModel.likePost(postId, type)
                                    },
                                    onShowReact = { postId, show ->
                                        postViewModel.setShowReactionState(show, postId)
                                    },
                                    modifier = Modifier,
                                    onPostClick = {

                                    },
                                    onUserClick = {
                                        navController.navigate("profile/${it.user.id}")
                                    },
                                    onGroupClick = {
                                        navController.navigate("group/${it.groupId}")
                                    }
                                )
                            }
                        }

                        posts.apply {
                            when {
                                loadState.append is androidx.paging.LoadState.Loading -> {
                                    item { Text(stringResource(R.string.loading_more)) }
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

            Spacer(modifier = Modifier.height(80.dp))

        }
    }
}

@Composable
fun CreatePostItem(
    onCreatePost: () -> Unit,
    modifier: Modifier,
    avatar: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(1000.dp)
            )
            .clickable {
                onCreatePost()
            }
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(1000.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = avatar,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.what_are_you_thinking),
            modifier = Modifier
                .padding(horizontal = 10.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp
        )
    }
}