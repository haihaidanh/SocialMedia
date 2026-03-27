package com.example.socialmedia1903.domain.model

import java.util.Date

data class User(
    val id: String,
    val name: String,
    val password: String,
    val avatarUrl:String,
    val createdAt: Date,
    val updatedAt: Date
)
