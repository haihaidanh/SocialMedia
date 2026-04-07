package com.example.socialmedia1903.presentation.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.decode.ImageSource
import com.example.socialmedia1903.R
import com.example.socialmedia1903.presentation.screen.profile.InvitationViewModel
import com.example.socialmedia1903.presentation.screen.profile.ProfileViewModel
import kotlinx.coroutines.delay
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    invitationViewModel: InvitationViewModel = hiltViewModel(),
    navController: NavController,
) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            OutlinedTextField(
                value = query,
                onValueChange = {
                    viewModel.onQueryChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
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
                        keyboardController?.hide()
                        val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
                        navController.navigate("result_screen?query=${encodedQuery}")
                    }
                )

            )

            Image(
                painter = painterResource(R.drawable.angry),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        keyboardController?.hide()
                        val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
                        navController.navigate("result_screen?query=${encodedQuery}")
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
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
                searchUserItem(
                    user,
                    onClick = {
                        navController.navigate("profile/${user.id}")
                    },
                    addFriendAction = {
                        invitationViewModel.addFriend(user.id)
                    }
                )
            }
        }
    }
}