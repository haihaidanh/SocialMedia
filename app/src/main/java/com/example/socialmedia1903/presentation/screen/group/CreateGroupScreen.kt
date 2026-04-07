package com.example.socialmedia1903.presentation.screen.group

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.socialmedia1903.R

@Composable
fun CreateGroupScreen(
    navController: NavController,
    groupViewModel: GroupViewModel = hiltViewModel()
) {
    var groupName by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val isCreating by groupViewModel.isDone.collectAsState()

    LaunchedEffect(isCreating) {
        if (isCreating) {
            navController.navigate("my-group") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .size(30.dp)
                    .clickable{
                        navController.popBackStack()
                    }
            )

            Text(
                text = "Tạo nhóm",
                fontSize = 20.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Avatar group
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Chọn ảnh")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tên nhóm
        OutlinedTextField(
            value = groupName,
            onValueChange = { groupName = it },
            label = { Text("Tên nhóm") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Trạng thái
        Text(text = "Trạng thái")

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = isPublic,
                onClick = { isPublic = true }
            )
            Text("Công khai")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = !isPublic,
                onClick = { isPublic = false }
            )
            Text("Riêng tư")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Button tạo nhóm
        Button(
            onClick = {
                groupViewModel.createGroup(
                    name = groupName,
                    status = if (isPublic) "public" else "private",
                    context = context,
                    avatarUri = imageUri ?: Uri.EMPTY
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tạo nhóm")
        }
    }
}