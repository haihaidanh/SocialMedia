package com.example.socialmedia1903.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.socialmedia1903.data.local.datastore.THEME_KEY
import com.example.socialmedia1903.data.local.datastore.dataStore
import com.example.socialmedia1903.domain.enums.ThemeMode
import com.example.socialmedia1903.domain.repository.SettingThemeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingThemeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingThemeRepository {
    override suspend fun saveTheme(theme: ThemeMode) {
        context.dataStore.edit {
            it[THEME_KEY] = theme.name
        }
    }

    override fun getAppTheme(): Flow<ThemeMode> {
        return context.dataStore.data.map {
            ThemeMode.valueOf(it[THEME_KEY] ?: ThemeMode.SYSTEM.name)
        }
    }


}