package com.example.socialmedia1903.domain.repository

import com.example.socialmedia1903.domain.enums.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingThemeRepository {
    suspend fun saveTheme(theme: ThemeMode)
    fun getAppTheme(): Flow<ThemeMode>
}