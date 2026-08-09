package com.example.socialmedia1903.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansBoldHelper
import com.example.socialmedia1903.presentation.core.modifier.staticStatusBarPadding
import com.example.socialmedia1903.presentation.screen.login.AuthInput
import com.example.socialmedia1903.presentation.screen.profile.InvitationViewModel
import kotlinx.coroutines.delay
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    invitationViewModel: InvitationViewModel = hiltViewModel(),
    navController: NavController,
) {
    val query by viewModel.text.collectAsState()
    val users by viewModel.users.collectAsState()

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
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .staticStatusBarPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = MaterialTheme.colorScheme.onSurface
            )

            AuthInput(
                searchQuery = query,
                onSearchQueryChange = {
                    viewModel.onQueryChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = "Tìm kiếm...",
                icon = R.drawable.search_home_icon,
                keyboardAction = {
                    keyboardController?.hide()
                    val encodedQuery =
                        URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
                    navController.navigate("result_screen?query=${encodedQuery}")
                }
            )
        }
        if (query.isNotEmpty() && users.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.people),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = fontOpenSansBoldHelper(),
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(users) { user ->
                SearchUserItem(
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