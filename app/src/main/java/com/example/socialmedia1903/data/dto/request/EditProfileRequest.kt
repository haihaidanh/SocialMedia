package com.example.socialmedia1903.data.dto.request

import com.example.socialmedia1903.domain.model.Friendship

data class EditProfileRequest(
    val name: String="",
    val gender: Int = 0,
    val birthday: String?=null,
    val username: String="",
    val phoneNumber: String?=null,
    val address: String?=null
)
