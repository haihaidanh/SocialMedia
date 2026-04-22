package com.example.socialmedia1903.data.local.preference

import android.content.Context
import com.example.socialmedia1903.data.dto.response.LogInResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyPreference @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveTokens(logInResponse: LogInResponse) {
        prefs.edit()
            .putString("access", logInResponse.accessToken)
            .putString("refresh", logInResponse.refreshToken)
            .putString("name", logInResponse.name)
            .putString("avatarUrl", logInResponse.avatarUrl)
            .putString("username", logInResponse.username)
            .putString("id", logInResponse.id)
            .apply()
    }

    fun getAccessToken(): String? {
        return prefs.getString("access", null)
    }

    fun getRefreshToken(): String?{
        return prefs.getString("refresh", null)
    }
    fun getName(): String?{
        return prefs.getString("name", null)
    }

    fun getAvatarUrl(): String?{
        return prefs.getString("avatarUrl", null)
    }

    fun getUsername(): String?{
        return prefs.getString("username", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun getUserId(): String? {
        return prefs.getString("id", null)
    }

}