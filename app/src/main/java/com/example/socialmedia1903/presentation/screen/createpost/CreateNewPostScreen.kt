package com.example.socialmedia1903.presentation.screen.createpost

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.presentation.component.Header
import com.example.socialmedia1903.presentation.component.RoundButton
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel
import com.example.socialmedia1903.presentation.theme.baseColor
import java.util.UUID

@Composable
fun CreateNewPostScreen(
    createPostViewModel: CreatePostViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController,
    groupId: String? = null
) {

    LaunchedEffect(Unit) {
        dashboardViewModel.getAvatar()
        dashboardViewModel.getUserName()
    }


    val avatarUrl by dashboardViewModel.avatar.collectAsState()
    val userName by dashboardViewModel.username.collectAsState()

    var content by remember { mutableStateOf("") }

    var visibility by remember { mutableStateOf(PostVisibility.PUBLIC) }

    var selectedImages by remember { mutableStateOf<List<String>>(emptyList()) }
    val context = LocalContext.current
    val isSaveToRoom by createPostViewModel.isSaveToRoom.collectAsState()
    val loadingSave by createPostViewModel.loadingSave.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current


    var postType by remember { mutableStateOf(PostType.TEXT) }

    if (selectedImages.isNotEmpty()) {
        postType = PostType.MEDIA
    }

    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        val selected = uris.map { it.toString() }
        selectedImages = selected

        uris.forEach { uri ->
            createPostViewModel.saveImageUri(uri.toString())
        }
    }

    var enableAnonymous by remember { mutableStateOf(false) }

    var background by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surface
            ).padding(horizontal = 10.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Header(
            onBackClick = {
                createPostViewModel.clearAllImages()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding(),
            title = stringResource(R.string.create_post)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = if(!enableAnonymous) ImageRequest.Builder(context)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build()
                else
                    R.drawable.anonymous,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        color = Color.Gray,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                userName?.let {
                    Text(
                        text = if(enableAnonymous) stringResource(R.string.anonymous) else it,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = fontOpenSansBoldHelper()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    VisibilityDropdown {
                        visibility = it
                    }

                    Icon(
                        painter = painterResource(R.drawable.public_icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.what_are_you_thinking),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontFamily = fontOpenSansBoldHelper()
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                focusedContainerColor = if (background != null) {
                    Color(android.graphics.Color.parseColor(background))
                } else {
                    MaterialTheme.colorScheme.surface
                },
                unfocusedContainerColor = if (background != null) {
                    Color(android.graphics.Color.parseColor(background))
                } else {
                    MaterialTheme.colorScheme.surface
                },
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item{
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = if(enableAnonymous)
                                baseColor
                            else
                                Color.Gray,
                            shape = CircleShape
                        )
                        .background(
                            color = if(enableAnonymous)
                                baseColor
                            else
                                MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
                        .clickable {
                            enableAnonymous = !enableAnonymous
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.anonymous),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if(enableAnonymous)
                            Color.White
                        else
                            Color.Gray
                    )
                }
            }


            items(actions()){ action ->
                RoundButton(
                    modifier = Modifier,
                    title = action.label,
                    icon = action.icon,
                    onClick = {
                        when (action.label) {
                            R.string.image -> {
                                mediaPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageAndVideo
                                    )
                                )
                            }

                            R.string.people -> {

                            }

                            R.string.feeling -> {
                                // Handle feeling action
                            }
                        }
                    }
                )
            }
        }

        PickColorRow(
          modifier = Modifier,
            colors = postBackgrounds,
            onColorSelected = { selectedColor ->
                background = selectedColor
            }
        )

        if (selectedImages.isNotEmpty()) {
            LazyRow {
                items(selectedImages) { uri ->

                    val mimeType = context.contentResolver.getType(uri.toUri())
                    val isVideo = mimeType?.startsWith("video") == true

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(uri.toUri())
                                .crossfade(true)
                                .videoFrameMillis(0)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        if (isVideo) {
                            Box(
                                modifier = Modifier.matchParentSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(
                                            Color.Black.copy(alpha = 0.5f),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (loadingSave) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        createPostViewModel.setLoadingSave(true)

                        if (isSaveToRoom) {
                            val posId = UUID.randomUUID().toString()
                            createPostViewModel.createPost(
                                postId = posId,
                                content = content,
                                type = postType,
                                groupId = groupId,
                                contentType = "plain",
                                anonymous = enableAnonymous,
                                visibility = visibility,
                                context = context,
                                background = background
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isSaveToRoom,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSaveToRoom) baseColor else Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.post)
                    )
                }
            }

        }
    }
}

data class ActionItem(
    val icon: Int,
    val label: Int,
)

fun actions(): List<ActionItem> {
    return listOf(
        ActionItem(
            icon = R.drawable.image_icon,
            label = R.string.image,
        ),
        ActionItem(
            icon = R.drawable.people_icon,
            label = R.string.people,
        ),
        ActionItem(
            icon = R.drawable.feeling_icon,
            label = R.string.feeling
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisibilityDropdown(
    onSelect: (PostVisibility) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(
        PostVisibility.PUBLIC
    ) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            postVisibleList.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier
                        ) {
                            Text(
                                text = stringResource(item.label),
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 4.dp)
                            )
                        }
                    },
                    onClick = {
                        onSelect(item.enum)
                        expanded = false
                    }
                )
            }
        }
    }
}

data class PostBackground(
    val name: String,
    val color: String?
)

val postBackgrounds = listOf(
    PostBackground("Red", "#FF5252"),
    PostBackground("Orange", "#FF9800"),
    PostBackground("Yellow", "#FFC107"),
    PostBackground("Green", "#4CAF50"),
    PostBackground("Cyan", "#00BCD4"),
    PostBackground("Blue", "#2196F3"),
    PostBackground("Purple", "#9C27B0"),
    PostBackground("Pink", "#E91E63"),
    PostBackground("Peach", "#FF8A80"),
    PostBackground("Light Blue", "#81D4FA"),
    PostBackground("Lavender", "#B39DDB"),
    PostBackground("Light Green", "#A5D6A7"),
    PostBackground("Brown", "#795548"),
    PostBackground("Gray", "#607D8B"),
    PostBackground("Black", "#212121"),
    PostBackground("White", "#FFFFFF")
)

@Composable
fun PickColorRow(
    modifier: Modifier,
    colors: List<PostBackground>,
    onColorSelected: (String?) -> Unit
){

    var selectedColor by remember { mutableStateOf<String?>(null) }

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        item{
            Image(
                painter = painterResource(id = R.drawable.pick_color_button),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = if (selectedColor == null)
                            MaterialTheme.colorScheme.onSurface
                        else
                            Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        selectedColor = null
                        onColorSelected(null)
                    }
            )
        }

        items(colors) { background ->
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = if (selectedColor == background.color)
                            MaterialTheme.colorScheme.onSurface
                        else
                            Color.Transparent,
                        shape = CircleShape
                    )
                    .background(
                        color = Color(android.graphics.Color.parseColor(background.color))
                    )
                    .clickable {
                        selectedColor = background.color
                        onColorSelected(selectedColor)
                    }
            )
        }
    }
}