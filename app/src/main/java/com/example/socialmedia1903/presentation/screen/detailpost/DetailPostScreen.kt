package com.example.socialmedia1903.presentation.screen.detailpost

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.dto.response.PostsResponse
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun DetailPostScreen(
    postId: String?,
    onBack: () -> Unit,
    postViewModel: PostViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        postId?.let { postViewModel.getDetailPost(it) }
    }

    LaunchedEffect(Unit) {
        postId?.let { postViewModel.getAllComment(it) }
    }

    val post by postViewModel.post.collectAsState()
    val comments by postViewModel.comments.collectAsState()
    val scrollState = rememberScrollState()

        Scaffold(
            topBar = {
                Header(post, onBack)
            },
        ) { padding ->
            Column(
                modifier = Modifier.padding(padding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(Color.White)
            ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray)
                    ) {
                        if (post.type == "media") {
                            //todo

                        } else {
                            Text(
                                text = post.content,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(20.dp)
                                    .align(Alignment.Center)
                                    .padding(10.dp)
                            )
                        }

                }

                Row {
                    Icon(
                        painter = painterResource(R.drawable.like),
                        contentDescription = "Like",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {  },
                        tint = Color.Blue
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(R.drawable.like_done),
                        contentDescription = "Share",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {  },
                        tint = Color.Gray
                    )
                }
                Divider()
                Text(
                    text = "Comments",
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Bold
                )

                comments.forEach{ comment ->
                    CommentItem(
                        comment.User.avatarUrl,
                        comment.User.name,
                        comment.content,
                        comment.createdAt.toString(),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

}

@Composable
fun Header(
    post: PostResponse,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Back button
        Icon(
            painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
            contentDescription = null,
            modifier = Modifier
                .clickable { onBack() }
                .padding(8.dp),
            tint = Color.Blue
        )

        Spacer(modifier = Modifier.width(8.dp))

        AsyncImage(
            model = post.User.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = if (post.anonymous) "Ẩn danh" else post.User.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = post.createdAt.toString(),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

