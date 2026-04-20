package com.example.socialmedia1903.presentation.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialmedia1903.R
import com.example.socialmedia1903.presentation.screen.login.LogInViewModel

@Composable
fun SplashScreen(
    logInViewModel: LogInViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        logInViewModel.checkLogin()
    }
    val authState by logInViewModel.authState.collectAsState()
    when (authState) {
        AuthState.LoggedIn -> {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        }

        AuthState.NotLoggedIn -> {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }

        AuthState.Loading -> {
            // Stay on the splash screen
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.base)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = Color.White
                ,
        )
    }
}