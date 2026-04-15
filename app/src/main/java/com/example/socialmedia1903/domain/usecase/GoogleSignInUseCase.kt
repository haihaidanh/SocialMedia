package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.GoogleSignInResponse
import com.example.socialmedia1903.data.source.AuthRepository
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<GoogleSignInResponse> {
        return  authRepository.signInWithGoogle(idToken)
    }
}