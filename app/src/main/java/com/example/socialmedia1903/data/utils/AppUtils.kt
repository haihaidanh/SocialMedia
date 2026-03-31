package com.example.socialmedia1903.data.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

object AppUtils {
    fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)

        val bytes = inputStream!!.readBytes()

        val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "file", // 👈 phải trùng req.file
            "image.jpg",
            requestBody
        )
    }
}