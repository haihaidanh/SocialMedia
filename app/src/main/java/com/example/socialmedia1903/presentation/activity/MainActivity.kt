package com.example.socialmedia1903.presentation.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.socialmedia1903.domain.enums.ThemeMode
import com.example.socialmedia1903.presentation.navigation.Graph
import com.example.socialmedia1903.presentation.navigation.Screen
import com.example.socialmedia1903.presentation.screen.createpost.CreateNewPostScreen
import com.example.socialmedia1903.presentation.screen.detailpost.DetailPostScreen
import com.example.socialmedia1903.presentation.screen.detailpost.PostViewModel
import com.example.socialmedia1903.presentation.screen.group.CreateGroupScreen
import com.example.socialmedia1903.presentation.screen.group.GroupListScreen
import com.example.socialmedia1903.presentation.screen.group.GroupScreen
import com.example.socialmedia1903.presentation.screen.home.HomeScreen
import com.example.socialmedia1903.presentation.screen.language.ChangeLanguageScreen
import com.example.socialmedia1903.presentation.screen.login.LoginScreen
import com.example.socialmedia1903.presentation.screen.profile.ProfileScreen
import com.example.socialmedia1903.presentation.screen.search.ResultSearchScreen
import com.example.socialmedia1903.presentation.screen.search.SearchScreen
import com.example.socialmedia1903.presentation.screen.setting.SettingScreen
import com.example.socialmedia1903.presentation.screen.setting.SettingsViewModel
import com.example.socialmedia1903.presentation.screen.signup.SignUpScreen
import com.example.socialmedia1903.presentation.screen.splash.SplashScreen
import com.example.socialmedia1903.presentation.screen.story.CameraScreen
import com.example.socialmedia1903.presentation.screen.story.PreviewVideoScreen
import com.example.socialmedia1903.presentation.theme.Socialmedia1903Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHideNavigationBar()

        setContent {
            val themeMode by viewModel.theme.collectAsState(initial = ThemeMode.SYSTEM)

            val isDark = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            Socialmedia1903Theme(darkTheme = isDark) {
                MainScreen()
            }
        }
    }

    private fun setHideNavigationBar() {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.apply {
                navigationBarColor = android.graphics.Color.TRANSPARENT

                decorView.windowInsetsController?.apply {
                    hide(WindowInsets.Type.navigationBars())

                    systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
            window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                lifecycleScope.launch {
                    delay(3000)
                    window.apply {
                        navigationBarColor = android.graphics.Color.TRANSPARENT

                        decorView.windowInsetsController?.apply {
                            hide(WindowInsets.Type.navigationBars())

                            systemBarsBehavior =
                                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        }
                    }
                }
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

            window.navigationBarColor = android.graphics.Color.TRANSPARENT
            window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                lifecycleScope.launch {
                    delay(3000)
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                    window.navigationBarColor = android.graphics.Color.TRANSPARENT
                }
            }
        }
        supportActionBar?.hide()
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.ChangeLanguage.route) {
            ChangeLanguageScreen(
                navController = navController
            )
        }

        navigation(
            route = Graph.Post.route,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Graph.Post.route)
                }
                val postViewModel: PostViewModel = hiltViewModel(parentEntry)
                HomeScreen(
                    navController = navController,
                    postViewModel = postViewModel
                )
            }

            composable(Screen.DetailPost.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Graph.Post.route)
                }
                val postViewModel: PostViewModel = hiltViewModel(parentEntry)
                DetailPostScreen(
                    navController = navController,
                    postViewModel = postViewModel
                )
            }
        }


        composable(
            route = Screen.CreatePost.route,
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            CreateNewPostScreen(
                navController = navController,
                groupId = groupId
            )
        }

        composable(Screen.MyGroup.route) {
            GroupListScreen(
                navController = navController
            )
        }

        composable(Screen.Group.route) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            GroupScreen(
                groupId = groupId,
                navController = navController
            )
        }

        composable(Screen.CreateGroup.route) {
            CreateGroupScreen(
                navController = navController
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.ResultScreen.route,
            arguments = listOf(navArgument("query") { defaultValue = "" })
        ) { backStackEntry ->
            val queryArg = backStackEntry.arguments?.getString("query") ?: ""
            ResultSearchScreen(
                query = queryArg,
                navController = navController
            )
        }

        composable(Screen.Profile.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            ProfileScreen(
                navController = navController,
                id = id ?: ""
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }

        composable(Screen.Setting.route) {
            SettingScreen(
                navController = navController
            )
        }

        composable(Screen.Groups.route) {
            GroupListScreen(
                navController = navController
            )
        }

        composable(Screen.OpenCamera.route) {
            CameraScreen(
                onVideoRecorded = { uri ->
                    navController.navigate(Screen.Preview.createScreen(videoUri = uri.toString()))
                },
                navController = navController
            )
        }

        composable(
            route = Screen.Preview.route,
            arguments = listOf(
                navArgument("videoUri") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val videoUri = backStackEntry.arguments?.getString("videoUri")!!
            PreviewVideoScreen(
                videoUri = videoUri.toUri(),
                onBack = { navController.popBackStack() },
                navController = navController
            )
        }
    }
}


