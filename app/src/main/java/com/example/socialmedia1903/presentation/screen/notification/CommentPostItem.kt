package com.example.socialmedia1903.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.domain.model.Notification


@Composable
fun CommentPostItem(
    notification: Notification,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Box(
            modifier = Modifier
                .size(40.dp),

            ) {
            AsyncImage(
                model = notification.user.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )


            Icon(
                painter = painterResource(R.drawable.comment_noti),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.BottomEnd)
                    .background(
                        color = Color(0xFF3B5998),
                        shape = CircleShape
                    )
                    .padding(5.dp)
            )
        }

            Text(text = "đã bình luận về bài viết của bạn: "+notification.commentContent, modifier = Modifier.padding(start = 12.dp), fontSize = 14.sp)

    }
}