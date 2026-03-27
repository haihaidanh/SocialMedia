package com.example.socialmedia1903.presentation.screen.detailpost

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CommentItem(
    avatarUrl: String?,
    username: String,
    content: String,
    createdAt: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {

        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            // Username
            Text(
                text = username,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            // Content
            Text(
                text = content,
                fontSize = 14.sp
            )

            // Thời gian
            Text(
                text = createdAt,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}