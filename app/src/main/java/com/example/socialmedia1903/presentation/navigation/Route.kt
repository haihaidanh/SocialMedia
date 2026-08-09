package com.example.socialmedia1903.presentation.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object MyGroup : Screen("my-groups")
    object CreateGroup : Screen("create-group")
    object Search : Screen("search")
    object SignUp : Screen("signup")
    object Setting : Screen("setting")
    object Groups : Screen("groups")
    object OpenCamera : Screen("open-camera")
    object ChangeLanguage : Screen("change_language")
    
    object DetailPost : Screen("detail/{postId}") {
        fun createScreen(postId: String) = "detail/$postId"
    }

    object Group : Screen("group/{groupId}") {
        fun createScreen(groupId: String) = "group/$groupId"
    }

    object Profile : Screen("profile/{id}") {
        fun createScreen(id: String) = "profile/$id"
    }

    object Preview : Screen("preview/{videoUri}") {
        fun createScreen(videoUri: String) = "preview/${Uri.encode(videoUri)}"
    }
    
    object CreatePost : Screen("create_post?groupId={groupId}") {
        fun createScreen(groupId: String? = null): String {
            return if (groupId != null) "create_post?groupId=$groupId" else "create_post"
        }
    }

    object ResultScreen : Screen("result_screen?query={query}") {
        fun createScreen(query: String = "") = "result_screen?query=$query"
    }
}