package com.example.socialmedia1903.data.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.socialmedia1903.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object AppUtils {
    fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)

        val bytes = inputStream!!.readBytes()

        val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "images",
            "image.jpg",
            requestBody
        )
    }

    fun formatDate(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun fontOpenSansBoldHelper(): FontFamily {
        return FontFamily(
            Font(R.font.opensans_bold)
        )
    }

    fun fontOpenSansSemiBoldHelper(): FontFamily {
        return FontFamily(
            Font(R.font.opensans_semibold)
        )
    }

    fun fontOpenSansMediumHelper(): FontFamily {
        return FontFamily(
            Font(R.font.opensans_medium)
        )
    }

    @SuppressLint("DefaultLocale")
    fun formatDuration(durationMs: Long): String {
        val totalSeconds = durationMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
}