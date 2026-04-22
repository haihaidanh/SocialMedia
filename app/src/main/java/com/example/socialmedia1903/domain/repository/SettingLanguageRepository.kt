package com.example.socialmedia1903.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingLanguageRepository {
    suspend fun saveLanguage(lang: String)
    fun getLanguage(): Flow<String>
}