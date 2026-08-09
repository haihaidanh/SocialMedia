package com.example.socialmedia1903.presentation.core.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.data.utils.AppUtils.formatDate
import com.example.socialmedia1903.domain.model.Post
import java.util.Date

@Composable
fun PostHeader(
    post: Post,
    modifier: Modifier,
    onEdit: (postId: String) -> Unit,
    onDelete: (postId: String) -> Unit,
    onStore: (postId: String) -> Unit,
    userId: String,
    onPostClick: () -> Unit= {},
    onUserClick: () -> Unit= {},
    onGroupClick: () -> Unit= {}
) {

    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        when (post.group) {
            null -> PostIndividualHeader(
                avatar = post.user.avatarUrl,
                createdAt = post.createdAt,
                name = post.user.username,
                modifier = Modifier,
                anonymous = post.anonymous,
                onClick = {
                    onUserClick()
                }
            )

            else -> PostGroupHeader(
                groupAvatar = post.group.imageUrl,
                groupName = post.group.name,
                userAvatar = post.user.avatarUrl,
                userName = post.user.username,
                modifier = Modifier,
                createdAt = post.createdAt,
                onGroupClick = {
                    onGroupClick()
                },
                onPostClick = {
                    onPostClick()
                },
                anonymous = post.anonymous
            )
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
                if (userId == post.authorId) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.edit_post)) },
                        onClick = {
                            expanded.value = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.delete_post)) },
                        onClick = {
                            expanded.value = false
                        }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.store_post)) },
                        onClick = {
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PostGroupHeader(
    groupAvatar: String,
    groupName: String,
    userAvatar: String,
    userName: String,
    createdAt: Date,
    modifier: Modifier,
    onGroupClick: () -> Unit,
    onPostClick: () -> Unit,
    anonymous: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {

        Box(
            modifier = Modifier.size(36.dp)
                .clickable { onGroupClick() }
        ) {
            AsyncImage(
                model = groupAvatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .align(Alignment.Center)

            )

            AsyncImage(
                model = if(anonymous)
                    R.drawable.anonymous
                else
                    userAvatar,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.surface,
                        CircleShape
                    )
                    .background(
                        color = Color.Gray,
                        shape = CircleShape
                    )
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
        ) {
            Text(
                text = groupName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = fontOpenSansBoldHelper(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable { onGroupClick() }
            )

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (anonymous) stringResource(R.string.anonymous) else userName,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = fontOpenSansMediumHelper(),
                )

                Text(
                    text = formatDate(createdAt),
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = fontOpenSansMediumHelper(),
                )
            }
        }
    }
}

@Composable
fun PostIndividualHeader(
    avatar: String,
    createdAt: Date,
    name: String,
    modifier: Modifier,
    anonymous: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier

    ) {
        AsyncImage(
            model = if (!anonymous) avatar else R.drawable.anonymous,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable { onClick() }
                .background(
                    color = Color.Gray,
                    shape = CircleShape
                ),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
        ) {
            Text(
                text = if (anonymous) stringResource(R.string.anonymous) else (name),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = fontOpenSansBoldHelper(),
                modifier = Modifier.clickable { onClick() }
            )

            Text(
                text = formatDate(createdAt),
                fontSize = 10.sp,
                color = Color.Gray,
                fontFamily = fontOpenSansMediumHelper()
            )
        }
    }
}