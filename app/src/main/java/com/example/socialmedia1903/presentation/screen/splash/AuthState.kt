package com.example.socialmedia1903.presentation.screen.splash

sealed class AuthState {
    data object Loading : AuthState()
    data object LoggedIn : AuthState()
    data object NotLoggedIn : AuthState()
}