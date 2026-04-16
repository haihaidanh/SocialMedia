package com.example.socialmedia1903.presentation.screen.dashboard.post

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.domain.model.Post

@Composable
fun PostHeader(
    post: Post,
    modifier: Modifier,
    onEdit: (Post) -> Unit,
    onDelete: (Post) -> Unit,
    onSave: (Post) -> Unit,
    userId: String
) {

    var expanded = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        if (post.groupId == null) {
            Row(verticalAlignment = Alignment.CenterVertically) {

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
                        text = if (post.anonymous) "Ẩn danh" else (post.user.username),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = post.createdAt.toString(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier.size(48.dp)
                ) {
                    AsyncImage(
                        model = post.group?.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .align(Alignment.Center)
                    )

                    AsyncImage(
                        model = post.user.avatarUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = post.group?.name!!,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = post.user.username,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterEnd)
                .size(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.option),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { expanded.value = true }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                Log.d("hai", "PostHeader: ${post.authorId}-${userId}")
                if (userId == post.authorId) {
                    DropdownMenuItem(
                        text = { Text("Chỉnh sửa bài viết") },
                        onClick = {
                            expanded.value = false
                            onEdit(post)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Xóa bài viết") },
                        onClick = {
                            expanded.value = false
                            onDelete(post)
                        }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text("Lưu bài viết") },
                        onClick = {
                            expanded.value = false
                            onSave(post)
                        }
                    )
                }
            }
        }
    }
}