package com.example.socialmedia1903.presentation.state

import com.example.socialmedia1903.data.dto.response.GoogleSignInResponse

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Success(val user: GoogleSignInResponse) : AuthState()
    data class Error(val message: String) : AuthState()
}