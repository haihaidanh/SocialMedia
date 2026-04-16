package com.example.socialmedia1903.presentation.screen.createpost

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.dto.response.MediaResponse
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel
import com.example.socialmedia1903.presentation.screen.dashboard.VideoPlayer
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
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getName()
    }

    val avatarUrl by dashboardViewModel.avatar.collectAsState()
    val userName by dashboardViewModel.name.collectAsState()


    var content by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf<List<String>>(emptyList()) }
    val context = LocalContext.current
    val isSaveToRoom by createPostViewModel.isSaveToRoom.collectAsState()
    val isSavePost by createPostViewModel.isSavePost.collectAsState()

    LaunchedEffect(isSavePost) {
        if (isSavePost) {
            Toast.makeText(context, "Post created successfully!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Log.d("hai", isSavePost.toString())

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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        createPostViewModel.clearAllImages()
                        navController.popBackStack()
                    }
            )

            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = userName ?: "User")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Content input
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            placeholder = { Text("Bạn đang nghĩ gì?") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            )
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    mediaPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageAndVideo
                        )
                    )
                },
                border = BorderStroke(1.dp, color = colorResource(id = R.color.base)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(
                    stringResource(R.string.choose_image),
                    color = colorResource(id = R.color.base)
                )
            }

            OutlinedButton(
                onClick = {

                },
                border = BorderStroke(1.dp, color = colorResource(id = R.color.base)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(stringResource(R.string.everyone), color = colorResource(id = R.color.base))
            }

            OutlinedButton(
                onClick = {

                },
                border = BorderStroke(1.dp, color = colorResource(id = R.color.base)),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(stringResource(R.string.feeling), color = colorResource(id = R.color.base))
            }


        }
        if (selectedImages.isNotEmpty()) {
            LazyRow {
                items(selectedImages) { uri ->

                    val mimeType = context.contentResolver.getType(Uri.parse(uri))
                    val isVideo = mimeType?.startsWith("video") == true

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(uri)
                                .crossfade(true)
                                .videoFrameMillis(0) // 🔥 quan trọng
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

        val loading = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            if(loading.value) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        loading.value = true
                        if (isSaveToRoom) {
                            val posId = UUID.randomUUID().toString()
                            createPostViewModel.createPost(
                                postId = posId,
                                content = content,
                                type = postType,
                                groupId = groupId,
                                contentType = "plain",
                                anonymous = false,
                                visibility = "public",
                                context = context
                            )

                            //createPostViewModel.clearAllImages()
                            //navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isSaveToRoom,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSaveToRoom) colorResource(id = R.color.base) else Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.post))
                }
            }

        }



    }
}
