package com.example.socialmedia1903.presentation.screen.notification

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.socialmedia1903.R

fun showNotification(context: Context, title: String?, body: String?, avatarUrl: String? = null) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    Log.d("hai", "showNotification: $title, $body, $avatarUrl")
    val notification = NotificationCompat.Builder(context, "channel_id")
        .setContentTitle(title ?: "No title")
        .setContentText(body ?: "No body")
        .setSmallIcon(R.drawable.edit)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
//    val bitmap = runCatching {
//        val url = java.net.URL(avatarUrl ?: return@runCatching null)
//        android.graphics.BitmapFactory.decodeStream(url.openConnection().getInputStream())
//    }.getOrNull()
//
//    bitmap?.let {
//        notification.setLargeIcon(it)
//    }

    manager.notify(System.currentTimeMillis().toInt(), notification.build())
}