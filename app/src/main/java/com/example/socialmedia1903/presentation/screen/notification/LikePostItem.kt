package com.example.socialmedia1903.presentation.screen.notification


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.NotificationResponse


@Composable
fun LikePostItem(
    notificationResponse: NotificationResponse,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                onClick()
            }
    ) {

        // 🔹 Header
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
            ) {
            AsyncImage(
                model = notificationResponse.User.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
                val image = when(notificationResponse.likeType){
                    "like" -> R.drawable.like_done
                    "love" -> R.drawable.love
                    "haha" -> R.drawable.haha
                    "wow" -> R.drawable.wow
                    "sad" -> R.drawable.sad
                    "angry" -> R.drawable.angry
                    else -> R.drawable.like
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(22.dp)
                    )
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row {
                    Text(
                        text = notificationResponse.User.name,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "đã thích bài viết của bạn: "+(notificationResponse.postContent ?: ""), maxLines = 1)
                }
            }
        }
    }
}