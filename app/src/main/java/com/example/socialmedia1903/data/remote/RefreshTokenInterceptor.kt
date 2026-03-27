package com.example.socialmedia1903.data.remote

import com.example.socialmedia1903.data.source.LocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

class RefreshTokenInterceptor(
    private val localDataSource: LocalDataSource,
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
                    localDataSource.clear()
                }
            }
        }

        return response
    }

    private fun refreshToken(): String? {
        return try {
            val refreshToken = localDataSource.getRefreshToken() ?: return null

            val response = apiService.refreshToken(
                mapOf("refreshToken" to refreshToken)
            ).execute()

            if (response.isSuccessful) {
                val body = response.body()

                val newAccess = body?.accessToken
                val newRefresh = body?.refreshToken
                //val newName = body?.

                if (!newAccess.isNullOrEmpty() && !newRefresh.isNullOrEmpty()) {
                    //localDataSource.saveTokens(newAccess, newRefresh)
                    newAccess
                } else null

            } else null

        } catch (e: Exception) {
            null
        }
    }
}