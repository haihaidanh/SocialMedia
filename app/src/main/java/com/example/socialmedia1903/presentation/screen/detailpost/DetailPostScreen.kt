package com.example.socialmedia1903.presentation.screen.detailpost

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.presentation.core.post.getIcon


@Composable
fun DetailPostScreen(
    navController: NavController,
    postViewModel: PostViewModel
) {
    val detailPost by postViewModel.post.collectAsState()
    val comments by postViewModel.comments.collectAsState()

    val icon = if (detailPost?.likes.isNullOrEmpty()) {
        R.drawable.like
    } else {
        getIcon(detailPost?.likes?.first()!!.type)
    }

    val likeIcon by remember { mutableIntStateOf(icon) }


    LaunchedEffect(Unit) {
        detailPost?.let {
            postViewModel.getAllComment(it.id)
            postViewModel.start(it.id)
        }

    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        detailPost?.let {
            Header(
                post = it,
                onBack = { navController.popBackStack() })
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                        detailPost?.let { detailPost ->
                            when (detailPost.type) {
                                PostType.MEDIA -> {
                                    detailPost.content?.let {
                                        Text(
                                            text = it,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }

                                }

                                PostType.TEXT -> {

                                }

                                else -> {}
                            }
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
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 10.dp)
                        )
                        detailPost?.let { post ->
                            Text(
                                text = post.likeCount.toString(),
                                fontSize = 14.sp
                            )
                        }

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
                            comment.user.avatarUrl,
                            comment.user.name,
                            comment.content,
                            comment.createdAt.toString(),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

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
                        detailPost?.let { detailPost ->
                            postViewModel.commentPost(detailPost.id,
                                null,
                                text
                            )
                        }
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
    post: Post,
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
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier
                .clickable { onBack() }
                .padding(8.dp),
            tint = Color.Blue
        )

        Spacer(modifier = Modifier.width(8.dp))

        AsyncImage(
            model = post.user.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = if (post.anonymous) "Ẩn danh" else post.user.name,
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


val appColors = listOf(
    // 1. Nhóm màu chủ đạo (Brand & Primary)
    Color(0xFF3B82F6), // Xanh Royal (Primary)
    Color(0xFF10B981), // Xanh Mint (Secondary)
    Color(0xFF8B5CF6), // Tím Trendy (Accent)

    // 2. Nhóm màu trạng thái (System & Feedback)
    Color(0xFFEF4444), // Đỏ Coral (Error/Danger)
    Color(0xFFF59E0B), // Cam Amber (Warning)

    // 3. Nhóm màu nền & Trung tính (Neutral)
    Color(0xFFF9FAFB), // Trắng Tuyết (Light Background)
    Color(0xFF111827), // Xám Charcoal (Dark Background)
    Color(0xFF1F2937), // Xám Slate (Card/Surface)

    // 4. Nhóm màu Text & Biên (Typography & Borders)
    Color(0xFF1F2937), // Đen Nhám (Primary Text)
    Color(0xFF9CA3AF)  // Xám Cool Gray (Secondary Text/Border)
)
