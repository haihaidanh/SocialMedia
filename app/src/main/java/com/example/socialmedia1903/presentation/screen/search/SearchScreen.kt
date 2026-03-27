package com.example.socialmedia1903.presentation.screen.search

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.domain.model.User
import kotlinx.coroutines.delay

@Composable
fun UserItem(
    user: User,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Avatar giả
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = user.name,
                fontWeight = FontWeight.Bold
            )
//            Text(
//                text = user.email,
//                fontSize = 12.sp,
//                color = Color.Gray
//            )
        }
    }
}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
){
    val query = viewModel.text.collectAsState().value
    val users = viewModel.users.collectAsState().value

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(500)
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔍 Search bar
        OutlinedTextField(
            value = query,
            onValueChange = {
                    viewModel.onQueryChange(it)
            },
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = { Text("Tìm kiếm...") },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // 🔹 Gán nút Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // 🔹 Ẩn bàn phím
                    keyboardController?.hide()
                    // 🔹 Chuyển sang screen khác
                    navController.navigate("result_screen") // truyền query nếu cần
                }
            )

        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Title
        if (query.isNotEmpty() && users.isNotEmpty()) {
            Text(
                text = "Mọi người",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // 🔹 List user
        LazyColumn {
            items(users) { user ->
                UserItem(user)
            }
        }
    }
}