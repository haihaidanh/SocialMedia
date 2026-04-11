package com.example.socialmedia1903.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.socialmedia1903.domain.enums.ThemeMode
import com.example.socialmedia1903.presentation.screen.createpost.CreateNewPostScreen
import com.example.socialmedia1903.presentation.screen.story.CameraScreen
import com.example.socialmedia1903.presentation.screen.dashboard.DashboardScreen
import com.example.socialmedia1903.presentation.screen.detailpost.DetailPostScreen
import com.example.socialmedia1903.presentation.screen.group.CreateGroupScreen
import com.example.socialmedia1903.presentation.screen.group.GroupListScreen
import com.example.socialmedia1903.presentation.screen.group.GroupScreen
import com.example.socialmedia1903.presentation.screen.login.LoginScreen
import com.example.socialmedia1903.presentation.screen.notification.NotificationScreen
import com.example.socialmedia1903.presentation.screen.profile.MyProfileScreen
import com.example.socialmedia1903.presentation.screen.profile.ProfileScreen
import com.example.socialmedia1903.presentation.screen.search.ResultSearchScreen
import com.example.socialmedia1903.presentation.screen.search.SearchScreen
import com.example.socialmedia1903.presentation.screen.setting.SettingScreen
import com.example.socialmedia1903.presentation.screen.setting.SettingsViewModel
import com.example.socialmedia1903.presentation.screen.signup.SignUpScreen
import com.example.socialmedia1903.presentation.screen.story.PreviewVideoScreen
import com.example.socialmedia1903.ui.theme.Socialmedia1903Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by viewModel.theme.collectAsState(initial = ThemeMode.SYSTEM)

            val isDark = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            Socialmedia1903Theme(
                darkTheme = isDark
            ) {
                mainScreen()
            }
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
                LoginScreen(navController = navController, paddingValues = padding)
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

            composable(
                route = "create_post?groupId={groupId}",
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                // Lấy groupId từ arguments
                val groupId = backStackEntry.arguments?.getString("groupId")
                CreateNewPostScreen(
                    navController = navController,
                    groupId = groupId
                )
            }

            composable("my-group") {
                GroupListScreen(
                    navController = navController
                )
            }

            composable("group/{groupId}") { backStackEntry ->
                val groupId = backStackEntry.arguments?.getString("groupId")
                GroupScreen(
                    groupId = groupId,
                    navController = navController
                )
            }

            composable("create-group") {
                CreateGroupScreen(
                    navController = navController
                )
            }

            composable("search") {
                SearchScreen(
                    navController = navController
                )
            }

            composable(
                route = "result_screen?query={query}",
                arguments = listOf(navArgument("query") { defaultValue = "" })
            ) { backStackEntry ->
                val queryArg = backStackEntry.arguments?.getString("query") ?: ""
                ResultSearchScreen(
                    query = queryArg,
                    navController = navController
                )
            }

            composable("profile/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                ProfileScreen(
                    navController = navController,
                    id = id ?: ""
                )
            }

            composable("my-profile") {
                MyProfileScreen(
                    navController = navController,
                    padding = padding
                )
            }
            composable("signup") {
                SignUpScreen(navController = navController)
            }

            composable("notification") {
                NotificationScreen(
                    navController = navController,
                    padding = padding
                )
            }

            composable("setting") {
                SettingScreen()
            }

            composable("groups") {
                GroupListScreen(
                    navController = navController
                )
            }

            composable("open-camera") {
                CameraScreen(
                    onVideoRecorded = { uri ->
                        navController.navigate("preview/${Uri.encode(uri.toString())}")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = "preview/{videoUri}",
                arguments = listOf(
                    navArgument("videoUri") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val videoUri = backStackEntry.arguments?.getString("videoUri")!!
                PreviewVideoScreen(
                    videoUri = Uri.parse(videoUri),
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }
        }
    }
}

