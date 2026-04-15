package com.example.socialmedia1903.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.socialmedia1903.domain.enums.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingThemeRepository @Inject constructor(
    @ApplicationContext private val context: Context
){
    suspend fun saveTheme(theme: ThemeMode) {
        context.dataStore.edit {
            it[THEME_KEY] = theme.name
        }
    }

    fun getAppTheme(): Flow<ThemeMode> {
        return context.dataStore.data.map {
            ThemeMode.valueOf(it[THEME_KEY] ?: ThemeMode.SYSTEM.name)
        }
    }

}