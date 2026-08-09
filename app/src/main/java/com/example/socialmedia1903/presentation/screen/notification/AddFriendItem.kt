package com.example.socialmedia1903.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.NotificationResponse
import com.example.socialmedia1903.domain.model.Notification
import com.example.socialmedia1903.presentation.component.BaseButton
import com.example.socialmedia1903.presentation.component.RoundButton

@Composable
fun AddFriendItem(
    item: Notification,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onItemClick: () -> Unit
) {
    var status by remember { mutableStateOf("none") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            ).padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AsyncImage(
            model = item.user.avatarUrl,
            contentDescription = "avatar",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.user.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        onItemClick()
                    },
                color = MaterialTheme.colorScheme.onSurface
            )
            when(status){
                "none" -> {
                    Text(
                        text = stringResource(id = R.string.friend_request_sent),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BaseButton(
                            title = R.string.accept,
                            onClick = {
                                status = "accepted"
                                onAccept()
                            },
                            modifier = Modifier.weight(1f),
                            radius = 1000,
                            textSize = 12,
                        )

                        RoundButton(
                            title = R.string.reject,
                            onClick = {
                                status = "rejected"
                                onReject()
                            },
                            modifier = Modifier.weight(1f),
                            radius = 1000,
                            textSize = 12,
                        )
                    }
                }
                "accepted" -> {
                    Text(
                        text = stringResource(R.string.friend_request_accepted),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                "rejected" -> {
                    Text(
                        text = stringResource(R.string.friend_request_declined),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

        }
    }
}

