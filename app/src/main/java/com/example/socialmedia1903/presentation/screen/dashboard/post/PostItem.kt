package com.example.socialmedia1903.presentation.screen.dashboard.post

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.domain.enums.ReactionType
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.presentation.screen.detailpost.PostViewModel
import com.example.socialmedia1903.presentation.screen.group.FriendListBottomSheet
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PostItemView(
    post: Post,
    navController: NavController,
    postViewModel: PostViewModel = hiltViewModel(),
    userId: String
) {

    var visibility by remember { mutableStateOf(post.visibility) }

    when (visibility) {
        PostVisibility.PUBLIC -> {
            PostItem(post = post,
                navController = navController,
                userId = userId,
                postViewModel = postViewModel,
                deleteClick = {
                visibility = PostVisibility.DELETED
            })
        }

        PostVisibility.FRIENDS -> {
            PostItem(post = post,
                navController = navController,
                postViewModel = postViewModel,
                userId = userId,
                deleteClick = {
                visibility = PostVisibility.DELETED
            })
        }

        PostVisibility.PRIVATE -> {

        }

        PostVisibility.DELETED -> {
            UndoItem(
                text = "Đã xóa thành công",
                onUndoClick = {
                    postViewModel.undoDeletePost(post.id)
                    visibility = PostVisibility.PUBLIC
                }
            )
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    postViewModel: PostViewModel,
    navController: NavController,
    userId: String,
    deleteClick: () -> Unit = {}
) {

    var likeCount by remember { mutableStateOf(post.likeCount) }
    val icon = if (post.likes.isNotEmpty()) {
        ReactionType.entries.find { it.title == post.likes[0].type }?.icon
    } else {
        R.drawable.like
    }
    var likeIcon by remember { mutableStateOf(icon) }
    var isLike by remember { mutableStateOf(if (post.likes.isEmpty()) false else true) }
    val commentCount by remember { mutableStateOf(post.commentCount) }
    val shareCount by remember { mutableStateOf(post.sharedCount) }
    var showReactions by remember { mutableStateOf(false) }
    var likeJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val friends by postViewModel.friends.collectAsState()


    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            postViewModel.getFriends()
        }
    }

    val context = LocalContext.current

    if (showBottomSheet) {
        FriendListBottomSheet(
            friends = friends,
            onInvite = { friendId ->

            },
            onDismiss = {
                showBottomSheet = false
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .clickable {
                    if (showReactions) {
                        showReactions = false
                    }
                }
        ) {

            PostHeader(
                post = post,
                modifier = Modifier,
                userId = userId,
                onEdit = {},
                onDelete = {
                    postViewModel.deletePost(post.id)
                    deleteClick()
                },
                onSave = {}
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                when (post.type) {
                    PostType.MEDIA -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = post.content,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(20.dp),
                                color = Color.Black
                            )
                            MediaSlider(
                                media = post.media
                            )

                        }
                    }

                    PostType.TEXT -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(Color.DarkGray)
                        ) {
                            Text(
                                text = post.content,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(20.dp)
                                    .align(Alignment.Center),
                                color = Color.White
                            )
                        }
                    }
                }

                if (showReactions) {
                    Row(
                        modifier = Modifier
                            .offset(y = 10.dp)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .align(Alignment.BottomCenter)
                            .padding(10.dp)
                            .background(
                                Color.LightGray,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        ReactionType.entries.forEach { type ->
                            Image(
                                painter = painterResource(type.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        if (!isLike) {
                                            likeCount++
                                            isLike = true
                                            likeIcon = type.icon
                                        }
                                        likeJob?.cancel()
                                        likeJob = scope.launch {
                                            delay(500)
                                            postViewModel.likePost(post.id, type.title)
                                            likeIcon = type.icon
                                            showReactions = false
                                        }
                                    }
                            )
                        }

//
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    // click nhanh: Like
                                    isLike = !isLike
                                    likeCount += if (isLike) 1 else -1
                                    likeIcon =
                                        if (isLike) R.drawable.like_done else R.drawable.like
                                    likeJob?.cancel()
                                    likeJob = scope.launch {
                                        delay(1000)
                                        if (isLike) {
                                            postViewModel.likePost(post.id, "like")
                                        } else {
                                            postViewModel.likePost(post.id, "unlike")
                                        }

                                    }
                                },
                                onLongPress = {
                                    // click giữ: hiện reactions
                                    showReactions = true
                                }
                            )
                        }
                        .padding(4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (likeIcon == R.drawable.like) {
                            Icon(
                                painter = painterResource(R.drawable.like),
                                contentDescription = "Like",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Image(
                                painter = painterResource(likeIcon!!),
                                contentDescription = "Like",
                                modifier = Modifier.size(24.dp),
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "$likeCount",
                            color = if (isLike) Color.Red else Color.Gray
                        )
                    }
                }

                // Comment button
                TextButton(onClick = {
                    if (post.id.isNotBlank()) {
                        Log.d("hai", "ahi" + post.id)
                        navController.navigate("detail/${post.id}")
                    } else {
                        Toast.makeText(context, "Post không hợp lệ", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.comment),
                        contentDescription = "Comment",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$commentCount")
                }

                // Share button
                TextButton(onClick = {
                    showBottomSheet = true

                }) {
                    Icon(
                        painter = painterResource(R.drawable.share),
                        contentDescription = "Share",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$shareCount")
                }
            }

        }
    }
}
