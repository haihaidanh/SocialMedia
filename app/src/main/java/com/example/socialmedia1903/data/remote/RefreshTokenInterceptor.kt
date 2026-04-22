package com.example.socialmedia1903.data.remote

import com.example.socialmedia1903.data.dto.response.LogInResponse
import com.example.socialmedia1903.data.local.preference.MyPreference
import okhttp3.Interceptor
import okhttp3.Response

class RefreshTokenInterceptor(
    private val myPreference: MyPreference,
    private val apiService: AppService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Nếu token hết hạn
        if (response.code == 401) {
            synchronized(this) {

                val newToken = refreshToken()

                if (!newToken.isNullOrEmpty()) {
                    val newRequest = request.newBuilder()
                        .header("Authorization", "Bearer $newToken")
                        .build()

                    response.close()
                    return chain.proceed(newRequest)
                } else {
                    // refresh fail → logout
                    myPreference.clear()
                }
            }
        }

        return response
    }

    private fun refreshToken(): String? {
        return try {
            val refreshToken = myPreference.getRefreshToken() ?: return null

            val response = apiService.refreshToken(
                mapOf("refreshToken" to refreshToken)
            ).execute()

            if (response.isSuccessful) {
                val body = response.body()

                val newAccess = body?.accessToken
                val newRefresh = body?.refreshToken
                val newName = body?.name
                val newAvatar = body?.avatar

                if (!newAccess.isNullOrEmpty() && !newRefresh.isNullOrEmpty()) {
                    myPreference.saveTokens(
                        LogInResponse(
                            accessToken = newAccess,
                            refreshToken = newRefresh,
                            name = newName ?: "",
                            avatarUrl = newAvatar ?: ""
                        )
                    )
                    newAccess
                } else null

            } else null

        } catch (e: Exception) {
            null
        }
    }
}