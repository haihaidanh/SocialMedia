package com.example.socialmedia1903.presentation.screen.dashboard

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.presentation.screen.dashboard.post.PostItem
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
    navController: NavController,
    padding: PaddingValues
) {

    LaunchedEffect(Unit) {
        storyViewModel.getStories()
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getAvatar()
    }

    val stories by storyViewModel.stories.collectAsState()

    val posts: LazyPagingItems<Post> = dashboardViewModel.posts.collectAsLazyPagingItems()
    //Log.d("hai", "Posts count: ${posts.itemCount}")
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val avatar by dashboardViewModel.avatar.collectAsState()

    LaunchedEffect(Unit) {
        dashboardViewModel.getUserId()
    }

    val userId by dashboardViewModel.userId.collectAsState()

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
                    searchOnClick = {
                        navController.navigate("search")
                    }
                )
            },
            bottomBar = {
                CustomBottomBarWithFab(navController) {
                    navController.navigate("create_post")
                }
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            item {
                                AddStoryItem(
                                    avatarUrl = avatar ?: "",
                                    onAddClick = {
                                        navController.navigate("open-camera")
                                    },
                                    modifier = Modifier
                                        .padding(vertical = 10.dp)
                                        .padding(start = 10.dp)
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
                                            .padding(10.dp)
                                    )
                                }
                            }

                        }

                    }

                    item {
                        createPostScreen(
                            onCreatePost = { navController.navigate("create_post") },
                            modifier = Modifier
                                .padding(5.dp),
                            avatar = avatar ?: ""
                        )
                    }


                    items(count = posts.itemCount) { index ->
                        val post = posts[index]
                        post?.let {
                            userId?.let { userId ->
                                PostItem(
                                    it,
                                    navController = navController,
                                    userId = userId
                                )
                            }
                        }
                    }
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
) {
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
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(text = "What are you thinking?..")
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current

    var aspectRatio by remember { mutableStateOf(16f / 9f) } // mặc định ngang

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = false

            addListener(object : Player.Listener {
                override fun onVideoSizeChanged(videoSize: VideoSize) {
                    val width = videoSize.width
                    val height = videoSize.height

                    if (width > 0 && height > 0) {
                        aspectRatio = width.toFloat() / height.toFloat()
                    }
                }
            })
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio),
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        }
    )
}