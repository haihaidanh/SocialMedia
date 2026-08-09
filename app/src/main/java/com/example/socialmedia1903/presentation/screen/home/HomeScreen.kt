package com.example.socialmedia1903.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.presentation.component.CustomBottomBarWithFab
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardScreen
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardViewModel
import com.example.socialmedia1903.presentation.screen.detailpost.PostViewModel
import com.example.socialmedia1903.presentation.screen.notification.NotificationScreen
import com.example.socialmedia1903.presentation.screen.profile.MyProfileScreen

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    postViewModel: PostViewModel
) {
    val state by homeViewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            "home" -> {
                DashboardScreen(
                    navController = navController,
                    postViewModel = postViewModel
                )
            }

            "features" -> {

            }

            "notification" -> {
                NotificationScreen(
                    navController = navController
                )
            }

            "my-profile" -> {
                MyProfileScreen(
                    navController = navController
                )
            }
        }

        CustomBottomBarWithFab(
            onAddClick = {
                navController.navigate("create_post")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            onItemClick = { item ->
                homeViewModel.setState(item)
            },
            selected = state
        )
    }
}