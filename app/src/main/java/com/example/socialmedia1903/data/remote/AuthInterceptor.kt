package com.example.socialmedia1903.data.remote

import com.example.socialmedia1903.data.local.MyPreference
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val myPreference: MyPreference
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = myPreference.getAccessToken()

        val request = chain.request().newBuilder()

        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }
}