package com.example.socialmedia1903.presentation.screen.dashboard

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.mapper.ReactionType
import com.example.socialmedia1903.presentation.screen.detailpost.PostViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PostItem(
    post: PostResponse,
    postViewModel: PostViewModel = hiltViewModel(),
    navController: NavController
) {

    var likeCount by remember { mutableStateOf(post.likeCount) }
    //Log.d("hai", post.type)
    val icon = if(post.Likes.isNotEmpty()){
        ReactionType.entries.find {it.title == post.Likes[0].type }?.icon
    } else {
        R.drawable.like
    }
    var likeIcon by remember { mutableStateOf(icon)}
    var isLike by remember { mutableStateOf(if (post.Likes.isEmpty()) false else true) }
    var commentCount by remember { mutableStateOf(post.commentCount) }
    var shareCount by remember { mutableStateOf(post.sharedCount) }
    var showReactions by remember { mutableStateOf(false) }
    var likeJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
                .clickable {
                    if(showReactions){
                        showReactions = false
                    }
                }
        ) {

            // Header (Author + Time)
            Row(verticalAlignment = Alignment.CenterVertically) {

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
                        text = if (post.anonymous) "Ẩn danh" else (post.User.name),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.createdAt.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Content
            if (post.content.isNotEmpty() && post.type == "media") {
                Text(
                    text = post.content,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            ) {
                if (post.type == "media") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Media Preview")
                    }
                } else {
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
            Spacer(modifier = Modifier.height(8.dp))
            // Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("$likeCount Likes")
                Text("$commentCount Comments")
                Text("$shareCount Shares")
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Like button
                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    // click nhanh: Like
                                    isLike = !isLike
                                    likeCount += if (isLike) 1 else -1
                                    likeIcon = if (isLike) R.drawable.like_done else R.drawable.like
                                    likeJob?.cancel()
                                    likeJob = scope.launch {
                                        delay(1000)
                                        if(isLike){
                                            postViewModel.likePost(post.id, "like")
                                        }else{
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
                        Image(
                            painter = painterResource(likeIcon!!),
                            contentDescription = "Like",
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "$likeCount",
                            color = if (isLike) Color.Red else Color.Gray
                        )
                    }
                }

                // Comment button
                TextButton(onClick = {
                    if(post.id.isNotBlank()){
                        Log.d("hai", "ahi"+ post.id)
                        navController.navigate("detail/${post.id}")
                    }else{
                        Toast.makeText(context, "Post không hợp lệ", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "Comment",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$commentCount")
                }

                // Share button
                TextButton(onClick = { /* handle share */ }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$shareCount")
                }
            }

        }
    }
}
