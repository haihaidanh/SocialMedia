package com.example.socialmedia1903.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")

val LANGUAGE_KEY = stringPreferencesKey("language")