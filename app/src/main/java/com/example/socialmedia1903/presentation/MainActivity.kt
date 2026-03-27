package com.example.socialmedia1903.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardScreen
import com.example.socialmedia1903.presentation.screen.detailpost.DetailPostScreen
import com.example.socialmedia1903.presentation.screen.login.LoginScreen
import com.example.socialmedia1903.ui.theme.Socialmedia1903Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            mainScreen()
        }
    }
}

@Composable
fun mainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route
    val bottomBarScreens = listOf("home", "profile")

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarScreens) {

            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {

            composable("login") {
                LoginScreen(navController = navController)
            }

            composable("detail/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                DetailPostScreen(
                    postId = postId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("home") {
                DashboardScreen(
                    padding = padding,
                    navController = navController
                )
            }

            composable("profile") {
                //ProfileScreen()
            }
        }
    }
}

