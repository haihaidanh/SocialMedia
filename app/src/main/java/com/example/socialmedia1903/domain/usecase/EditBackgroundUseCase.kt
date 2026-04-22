package com.example.socialmedia1903.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.socialmedia1903.domain.repository.UserRepository
import javax.inject.Inject

class EditBackgroundUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uri: Uri) {
        userRepository.editBackground(uri)
    }
}