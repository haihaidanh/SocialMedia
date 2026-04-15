package com.example.socialmedia1903.presentation.screen.group

import android.util.Log
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.data.dto.response.GroupInfoResponse
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel
import com.example.socialmedia1903.presentation.screen.dashboard.post.PostItem
import com.example.socialmedia1903.presentation.screen.dashboard.createPostScreen
import com.example.socialmedia1903.presentation.screen.profile.InvitationViewModel
import com.example.socialmedia1903.presentation.screen.profile.BottomSheet
import com.example.socialmedia1903.presentation.screen.profile.BottomSheetItem
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
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getAvatar()
    }

    LaunchedEffect(Unit) {
        groupViewModel.getFriends()
    }

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

    //phản hồi
    BottomSheet(
        show = showResponse,
        content = listOf(
            BottomSheetItem(
                "Đồng ý",
                onClick = {

                }
            ),
            BottomSheetItem(
                "Từ chối",
                onClick = {

                }
            )
        ),
        onDismiss = {
            showResponse = false
        }
    )

    BottomSheet(
        show = showLeaveGroupBottomSheet,
        content = listOf(
            BottomSheetItem("Rời nhóm") {
                groupViewModel.leaveGroup(groupId)
                showLeaveGroupBottomSheet = false
            }
        ),
        onDismiss = { showLeaveGroupBottomSheet = false }
    )


    // Thay thế đoạn khai báo cũ bằng đoạn này:
    val maxHeaderHeight = 150.dp
    val headerHeightPx = with(LocalDensity.current) { maxHeaderHeight.toPx() }

// Biến này quyết định Column đang nằm ở đâu (bắt đầu từ 200dp)
    var contentOffsetY by remember { mutableStateOf(headerHeightPx) }


// ... (Các biến maxHeaderHeight, headerHeightPx giữ nguyên)


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


// Tạo hiệu ứng chuyển màu mượt mà cho background của Topbar
    val topBarBackgroundColor by animateColorAsState(
        targetValue = if (isCollapsed) Color.White else Color.Transparent,
        label = "TopBarColor"
    )

// Tạo hiệu ứng chuyển màu cho Icon/Text (Trắng khi trên ảnh, Đen khi trên nền trắng)
    val contentColor by animateColorAsState(
        targetValue = if (isCollapsed) Color.Black else Color.White,
        label = "ContentColor"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection) // Gán bộ lắng nghe cuộn vào đây
    ) {
        // 1. ẢNH BÌA (Header)
        AsyncImage(
            model = group.imageUrl,
            contentDescription = "Cover Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeaderHeight)
                .background(Color.White)
        )

        // 2. NỘI DUNG CHÍNH (Chiếm diện tích còn lại và đẩy lên)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    // Di chuyển Column dựa trên độ cuộn của Header
                    IntOffset(x = 0, y = contentOffsetY.roundToInt())
                }
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        ) {


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Phần thông tin nhóm
                item {
                    GroupTitle(
                        group = group,
                        navController = navController,
                        showFriendDialog = { showFriendDialog = true },
                        showBottomSheet = {
                            showLeaveGroupBottomSheet = true
                        },
                        showResponse = {
                            showResponse = true
                        }
                    )
                }

                // Phần tạo bài viết
                item {
                    createPostScreen(
                        onCreatePost = { navController.navigate("create_post?groupId=${groupId}") },
                        modifier = Modifier.padding(10.dp),
                        avatar = avatar ?: ""
                    )
                }

                // Tiêu đề bài viết
                item {
                    Text(
                        text = "Bài viết",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Danh sách bài viết (Dùng items thay vì forEach để tối ưu hiệu năng)
                items(posts) { post ->
                    PostItem(post, navController = navController)
                }

                // Thêm Spacer cuối cùng để tránh bị che bởi lề dưới nếu cần
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }


        // 3. TOPBAR DỊCH CHUYỂN & BIẾN ĐỔI
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Chiều cao chuẩn của TopAppBar
                .background(topBarBackgroundColor)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(48.dp) // Kích thước bao gồm cả padding cho dễ bấm
                    .padding(12.dp)
                    .clickable { navController.popBackStack() },
                tint = contentColor
            )

            // Hiển thị tên nhóm khi đã cuộn lên
            if (isCollapsed) {
                Text(
                    text = group.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


@Composable
fun GroupTitle(
    group: GroupInfo,
    navController: NavController,
    showFriendDialog: () -> Unit,
    showBottomSheet: () -> Unit,
    showResponse: () -> Unit
) {


    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = group.name,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (group.status == "public") "Nhóm công khai" else "Nhóm riêng tư",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = group.memberCount.toString() + " thành viên",
                fontSize = 16.sp,
                color = Color.Gray
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
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cài Đặt",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = {
                        showFriendDialog()
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp)
                        .clickable {
                            showFriendDialog()
                        },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Mời",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else if (group.memberGroups.isNotEmpty()) {
                Button(
                    onClick = {
                        showBottomSheet()
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)

                ) {
                    Text(
                        text = "Đã tham gia",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = {
                        showFriendDialog()
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)

                ) {

                    Text(
                        text = "Mời",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else if (group.statusRequest == "PENDING") {

                Button(
                    onClick = {
                        showResponse()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Phản hồi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Tham gia",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}