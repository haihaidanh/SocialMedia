package com.example.socialmedia1903.presentation.screen.detailpost

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.mapper.ReactionType


@Composable
fun DetailPostScreen(
    postId: String?,
    onBack: () -> Unit,
    postViewModel: PostViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        postId?.let { postViewModel.getDetailPost(it) }
    }
    val comments by postViewModel.comments.collectAsState()
    LaunchedEffect(comments) {
        postId?.let { postViewModel.getAllComment(it) }
    }
    LaunchedEffect(Unit) {
        postId?.let { postViewModel.start(it) }
    }

    val post by postViewModel.post.collectAsState()

    val icon = if (post.Likes.isEmpty()) {
        R.drawable.like
    } else {
        ReactionType.entries.find { it.title == post.Likes[0].type }?.icon ?: R.drawable.like
    }

    val likeIcon by remember { mutableStateOf(icon) }

    Log.d("VM", comments.toString())
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Header(post, onBack)
        },
    ) { padding ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
            ) {

                val (content, input) = createRefs()

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .background(Color.White)
                        .constrainAs(content) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(input.top)
                            height = Dimension.fillToConstraints
                        }
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray)
                    ) {
                        if (post.type != "media") {
                            Text(
                                text = post.content,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(likeIcon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                                .padding(end = 10.dp)
                        )
                        Text(post.likeCount.toString(), fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(R.drawable.share),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Divider()

                    Text(
                        text = "Comments",
                        modifier = Modifier.padding(12.dp),
                        fontWeight = FontWeight.Bold
                    )

                    comments.forEach { comment ->
                        CommentItem(
                            comment.User.avatarUrl,
                            comment.User.name,
                            comment.content,
                            comment.createdAt.toString(),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // 🔥 INPUT
                TypeComment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .constrainAs(input) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    onSend = { text ->
                        postId?.let { postViewModel.commentPost(it, null, text) }
                    }
                )
            }
        }
    }
}


@Composable
fun TypeComment(
    onSend: (String) -> Unit = { },
    modifier: Modifier
) {
    var comment by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Nhập comment...") },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (comment.isNotBlank()) {
                        onSend(comment)
                        comment = ""
                    }
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        Button(
            onClick = {
                if (comment.isNotBlank()) {
                    onSend(comment)
                    comment = ""
                }
            }
        ) {
            Text("Send")
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
            .padding(12.dp)
            .background(Color.White),
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

