package com.example.socialmedia1903.presentation.screen.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.domain.model.User
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel


@Composable
fun MyProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        profileViewModel.getMyProfile()
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getUserId()
    }

    val userId by dashboardViewModel.userId.collectAsState()

    val scroll = rememberScrollState()
    val profile by profileViewModel.profile.collectAsState()
    val posts by profileViewModel.posts.collectAsState()
    val friends by profileViewModel.friends.collectAsState()
    var selectedIndex by remember { mutableIntStateOf(R.string.all) }
    var isEditInfo by remember { mutableStateOf(false) }
    var image by remember { mutableStateOf<String?>(null) }
    var description by remember { mutableStateOf("") }
    var isEditDescription by remember { mutableStateOf(false) }

    profile.background?.let {
        image = it
    }

    profile.description?.let {
        description = it
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        image = uri.toString()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .staticStatusBarPadding()
            .background(
                color = MaterialTheme.colorScheme.surface
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {

                AsyncImage(
                    model = image ?: R.drawable.background,
                    contentDescription = "Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.LightGray)
                        .align(Alignment.TopCenter)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = (-10).dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(
                            MaterialTheme.colorScheme.surface
                        )
                )
                AsyncImage(
                    model = profile.avatarUrl,
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 30.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(3.dp, Color.White, CircleShape)
                        .align(Alignment.BottomStart)
                )

                Icon(
                    painter = if (!image.isNullOrEmpty())
                        painterResource(id = R.drawable.agree)
                    else
                        painterResource(id = R.drawable.edit),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(18.dp)
                        .clickable {
                            if (!image.isNullOrEmpty()) {
                                profileViewModel.editBackground(Uri.parse(image))
                                image = null
                            } else {
                                launcher.launch("image/*")
                            }


                        }
                        .align(Alignment.TopEnd)
                        .zIndex(10f)
                )
            }

            Text(
                text = profile.username,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "@" + profile.name,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp),
                fontWeight = FontWeight.SemiBold
            )

            TextAndIcon(
                text = stringResource(R.string.desc),
                icon = R.drawable.edit,
                onIconClick = {
                    isEditDescription = !isEditDescription
                },
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            if (isEditDescription) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.agree),
                            contentDescription = null,
                            tint = colorResource(R.color.base),
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    isEditDescription = false
                                }
                        )
                    }
                )
            } else {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(tabList) { tab ->
                    val isSelected = selectedIndex == tab
                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(1000.dp)
                            )
                            .clickable {
                                selectedIndex = tab
                            }
                            .background(
                                color = if (isSelected)
                                    colorResource(R.color.base)
                                else
                                    MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(1000.dp)
                            ),
                    ) {
                        Text(
                            text = stringResource(id = tab),
                            color = if (isSelected)
                                Color.White
                            else
                                MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .align(
                                    Alignment.Center
                                ),
                            fontFamily = fontOpenSansBoldHelper()
                        )
                    }
                }
            }

            when (selectedIndex) {
                R.string.all -> {
                    TextAndIcon(
                        text = stringResource(R.string.personal_profile),
                        icon = R.drawable.edit,
                        onIconClick = {

                        }
                    )
                    FriendsPreview(
                        friends = friends,
                        onSeeAllClick = {

                        },
                        navController,
                        modifier = Modifier
                            .padding(10.dp)
                    )
//                    posts.forEach { post ->
//                        userId?.let {
//                            PostItemView(
//                                post = post,
//                                userId = it,
//                                onCommentClick = {
//                                },
//                                modifier = Modifier
//                            )
//                        }
//
//                    }
                }

                R.string.personal_profile -> {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Thông tin cá nhân",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontOpenSansBoldHelper()
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    isEditInfo = !isEditInfo
                                }
                        )
                    }


                    EditableTextComponent(
                        edit = isEditInfo,
                        text = "Phone Number",
                        content = profile.phoneNumber ?: "",
                        onSave = {

                        }
                    )
                    EditableTextComponent(
                        edit = isEditInfo,
                        text = "Address",
                        content = profile.birthday ?: "",
                        onSave = {}
                    )

                    EditableTextComponent(
                        edit = isEditInfo,
                        text = "Birthday",
                        content = profile.birthday ?: "",
                        onSave = {}
                    )

                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Lưu")
                    }
                }

                R.string.photos -> {
                    Text(
                        text = "Ảnh",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.height(220.dp),
                        userScrollEnabled = false
                    ) {
                        items(friends.take(6)) { friend ->
                            FriendItem(
                                friend,
                                onClick = {
                                    navController.navigate("profile/${friend.id}")
                                }
                            )
                        }
                    }
                }
            }


        }
    }
}


@Composable
fun FriendsPreview(
    friends: List<User>,
    onSeeAllClick: () -> Unit,
    navController: NavController,
    modifier: Modifier
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.friends),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stringResource(R.string.see_all),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable { onSeeAllClick() },
                fontSize = 12.sp,

            )
        }

        // Grid 2 hàng 3 cột
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(220.dp),
            userScrollEnabled = false
        ) {
            items(friends.take(6)) { friend ->
                FriendItem(
                    friend,
                    onClick = {
                        navController.navigate("profile/${friend.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun EditableTextComponent(
    edit: Boolean = false,
    text: String,
    content: String,
    onSave: (String) -> Unit
) {
    var ct by remember { mutableStateOf(content) }

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )


        if (edit) {
            OutlinedTextField(
                value = ct,
                onValueChange = { ct = it },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black,
                )
            )
        } else {
            Text(
                text = ct,
                fontSize = 16.sp
            )
            onSave(ct)
        }
    }
}

@Composable
fun TextAndIcon(
    text: String,
    icon: Int,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(12.dp)
                .clickable { onIconClick() }
        )
    }
}

@Composable
fun FriendItem(
    friend: User,
    onClick: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = friend.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = friend.name,
            fontSize = 16.sp,
            maxLines = 1
        )
    }
}

val tabList = listOf(
    R.string.all,
    R.string.info,
    R.string.photos
)

