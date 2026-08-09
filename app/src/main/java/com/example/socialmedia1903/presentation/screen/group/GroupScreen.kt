package com.example.socialmedia1903.presentation.screen.group

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.presentation.component.BaseButton
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.core.post.PostItemView
import com.example.socialmedia1903.presentation.screen.dashboard.CreatePostItem
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel
import com.example.socialmedia1903.presentation.screen.profile.BottomSheet
import com.example.socialmedia1903.presentation.screen.profile.BottomSheetItem
import com.example.socialmedia1903.presentation.screen.profile.InvitationViewModel
import kotlin.math.roundToInt

@Composable
fun GroupScreen(
    navController: NavController,
    groupId: String? = null,
    groupViewModel: GroupViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    invitationViewModel: InvitationViewModel = hiltViewModel()

) {

    LaunchedEffect(Unit) {
        groupId?.let {
            groupViewModel.getGroupDetail(it)
        }
        dashboardViewModel.getAvatar()
        groupViewModel.getFriends()
        dashboardViewModel.getUserId()
    }

    val userId by dashboardViewModel.userId.collectAsState()

    val group by groupViewModel.group.collectAsState()
    val posts by groupViewModel.posts.collectAsState()
    val avatar by dashboardViewModel.avatar.collectAsState()
    val friends by groupViewModel.friends.collectAsState()
    Log.d("hai", friends.size.toString())
    var showFriendDialog by remember { mutableStateOf(false) }
    var showLeaveGroupBottomSheet by remember { mutableStateOf(false) }
    var showResponse by remember { mutableStateOf(false) }

    if (showFriendDialog) {
        FriendListBottomSheet(
            friends = friends,
            onDismiss = { showFriendDialog = false },
            onInvite = { friendId ->
                invitationViewModel.inviteGroup(groupId, friendId)
            }
        )
    }

    val maxHeaderHeight = 150.dp
    val headerHeightPx = with(LocalDensity.current) { maxHeaderHeight.toPx() }

    var contentOffsetY by remember { mutableStateOf(headerHeightPx) }


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                // Khi vuốt lên (delta < 0) và Column vẫn chưa chạm đỉnh (offset > 0)
                if (delta < 0 && contentOffsetY > 0f) {
                    val newOffset = (contentOffsetY + delta).coerceAtLeast(0f)
                    val consumed = newOffset - contentOffsetY
                    contentOffsetY = newOffset
                    return Offset(0f, consumed) // Trả về số pixel đã dùng để dịch chuyển Column
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                // Khi vuốt xuống (delta > 0) và LazyColumn đã cuộn lên trên cùng
                if (delta > 0 && contentOffsetY < headerHeightPx) {
                    val newOffset = (contentOffsetY + delta).coerceAtMost(headerHeightPx)
                    val consumedValue = newOffset - contentOffsetY
                    contentOffsetY = newOffset
                    return Offset(0f, consumedValue)
                }
                return Offset.Zero
            }
        }
    }

// Trạng thái lấp đầy ảnh bìa (dùng để hiện Topbar)
    val topBarHeightPx = with(LocalDensity.current) { 56.dp.toPx() }
    val isCollapsed = contentOffsetY <= topBarHeightPx


    val topBarBackgroundColor by animateColorAsState(
        targetValue = if (isCollapsed)
            MaterialTheme.colorScheme.surface
        else
            Color.Transparent,
        label = "TopBarColor",

    )

    val contentColor by animateColorAsState(
        targetValue = if (isCollapsed)
            MaterialTheme.colorScheme.onSurface
        else
            MaterialTheme.colorScheme.onBackground,
        label = "ContentColor"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .background(
                MaterialTheme.colorScheme.surface
            )
    ) {
        AsyncImage(
            model = group.imageUrl,
            contentDescription = "Cover Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeaderHeight)

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(x = 0, y = contentOffsetY.roundToInt())
                }
                .clip(
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .background(
                    MaterialTheme.colorScheme.background,
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    GroupTitle(
                        group = group,
                        showFriendDialog = { showFriendDialog = true },
                        showBottomSheet = {
                            showLeaveGroupBottomSheet = true
                        },
                        showResponse = {
                            showResponse = true
                        }
                    )
                }

                item {
                    CreatePostItem(
                        onCreatePost = {
                            navController.navigate("create_post?groupId=${groupId}")
                        },
                        modifier = Modifier.padding(10.dp),
                        avatar = avatar ?: ""
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.posts),
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = fontOpenSansBoldHelper()
                    )
                }

                items(posts) { post ->
                    userId?.let { userId ->
                        PostItemView(
                            post,
                            userId = userId,
                            onCommentClick = {

                            },
                            modifier = Modifier,
                            showReactions = false,
                            onLikePost = { postId, type ->

                            },
                            onShowReact = { postId, show ->

                            },
                            onGroupClick = {},
                            onPostClick = {},
                            onUserClick = {
                                navController.navigate("profile/${post.authorId}")
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding()
                .height(56.dp)
                .background(topBarBackgroundColor)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
                    .clickable {
                        navController.popBackStack()
                               },
                tint = contentColor
            )

            if (isCollapsed) {
                Text(
                    text = group.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp),
                    fontFamily = fontOpenSansBoldHelper(),
                    color = contentColor
                )
            }
        }
        BottomSheet(
            visibility = showLeaveGroupBottomSheet,
            content = listOf(
                BottomSheetItem(
                    content = stringResource(R.string.leave),
                    onClick = {
                        groupViewModel.leaveGroup(groupId)
                        showLeaveGroupBottomSheet = false
                    }
                )
            ),
            onDismiss = { showLeaveGroupBottomSheet = false }
        )

        BottomSheet(
            visibility = showResponse,
            content = listOf(
                BottomSheetItem(
                    content = stringResource(R.string.accept),
                    onClick = {

                    }
                ),
                BottomSheetItem(
                    content = stringResource(R.string.reject),
                    onClick = {

                    }
                )
            ),
            onDismiss = {
                showResponse = false
            }
        )
    }
}


@Composable
fun GroupTitle(
    group: GroupInfo,
    showFriendDialog: () -> Unit,
    showBottomSheet: () -> Unit,
    showResponse: () -> Unit
) {


    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            text = group.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = fontOpenSansBoldHelper()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (group.status == "public")
                    stringResource(R.string.public_group)
                else
                    stringResource(R.string.private_group),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(
                    R.string.member,
                    group.memberCount.toString()
                ),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = fontOpenSansBoldHelper()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (group.isOwner) {
                BaseButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp),
                    radius = 12,
                    textSize = 16,
                    title = R.string.settings
                )

                BaseButton(
                    onClick = {
                        showFriendDialog()
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp),
                    radius = 12,
                    textSize = 16,
                    title = R.string.invite
                )
            } else if (group.memberGroups.isNotEmpty()) {

                BaseButton(
                    onClick = {
                        showBottomSheet()
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp),
                    radius = 12,
                    textSize = 16,
                    title = R.string.joined
                )

                BaseButton(
                    onClick = {
                        showFriendDialog()
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp),
                    radius = 12,
                    textSize = 16,
                    title = R.string.invite
                )
            } else if (group.statusRequest == "PENDING") {
                BaseButton(
                    onClick = {
                        showResponse()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    radius = 12,
                    textSize = 16,
                    title = R.string.response
                )
            } else {

                BaseButton(
                    onClick = {
                        showFriendDialog()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    radius = 12,
                    textSize = 16,
                    title = R.string.join
                )
            }
        }
    }
}