package com.example.socialmedia1903.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.socialmedia1903.data.local.datastore.LANGUAGE_KEY
import com.example.socialmedia1903.data.local.datastore.dataStore
import com.example.socialmedia1903.domain.repository.SettingLanguageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingLanguageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingLanguageRepository{

    override suspend fun saveLanguage(lang: String) {
        context.dataStore.edit {
            it[LANGUAGE_KEY] = lang
        }
    }

    override fun getLanguage(): Flow<String> {
        return context.dataStore.data.map {
            it[LANGUAGE_KEY] ?: "en"
        }
    }
}