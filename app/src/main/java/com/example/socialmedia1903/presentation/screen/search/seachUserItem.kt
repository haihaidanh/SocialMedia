package com.example.socialmedia1903.presentation.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedia1903.data.dto.response.SearchItemResponse
import com.example.socialmedia1903.domain.enums.InvitationStatus
import com.example.socialmedia1903.domain.model.SearchItem

@Composable
fun searchUserItem(
    user: SearchItem,
    onClick: () -> Unit = {},
    addFriendAction: () -> Unit = {}
) {
    val status = user.status == InvitationStatus.PENDING

    var isPending by remember { mutableStateOf(status) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(vertical = 8.dp)
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = user.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                if(user.status == InvitationStatus.ACCEPTED){
                    Text(
                        text = "Bạn bè",
                        fontSize = 14.sp,
                    )
                }
            }
        }

        if(user.status != InvitationStatus.ACCEPTED){
            Button(
                onClick = {
                    addFriendAction()
                    isPending = true
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(isPending) Color.Green else Color.Blue
                )
                ) {
                Text(
                    text = if(isPending) "Đã gửi" else "Kết bạn"
                )
            }
        }



    }
}