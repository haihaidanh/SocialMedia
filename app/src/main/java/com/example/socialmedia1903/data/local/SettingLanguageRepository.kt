package com.example.socialmedia1903.data.local

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingLanguageRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun saveLanguage(lang: String) {
        context.dataStore.edit {
            it[LANGUAGE_KEY] = lang
        }
    }

    fun getLanguage(): Flow<String> {
        return context.dataStore.data.map {
            it[LANGUAGE_KEY] ?: "en"
        }
    }
}