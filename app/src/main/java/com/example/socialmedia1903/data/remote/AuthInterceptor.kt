package com.example.socialmedia1903.data.remote

import com.example.socialmedia1903.data.source.LocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val localDataSource: LocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = localDataSource.getAccessToken()

        val request = chain.request().newBuilder()

        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }
}