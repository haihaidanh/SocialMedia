package com.example.socialmedia1903.presentation.screen.createpost

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel
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
        if(isSavePost){
            Toast.makeText(context, "Post created successfully!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Log.d("hai", isSavePost.toString())

    var postType by remember { mutableStateOf("text") }

    if(selectedImages.isNotEmpty()){
        postType = "image"
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        selectedImages = uris.map { it.toString() }
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

        Spacer(modifier = Modifier.height(12.dp))

        // Slider demo
        ImageSlider(
            images = selectedImages
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Pick images
        Button(onClick = {
            imagePickerLauncher.launch("image/*")
        }) {
            Text("Chọn ảnh")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Preview images
        if (selectedImages.isNotEmpty()) {
            LazyRow {
                items(selectedImages) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                if(isSaveToRoom){
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
                enabled = isSaveToRoom) {
                Text("Post")
            }
        }
    }
}

@Composable
fun ImageSlider(
    images: List<String>
) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                val color = if (pagerState.currentPage == index) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Gray
                }

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}