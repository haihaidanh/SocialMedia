package com.example.socialmedia1903.presentation.screen.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.socialmedia1903.R

fun showNotification(context: Context, title: String?, body: String?) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notification = NotificationCompat.Builder(context, "channel_id")
        .setContentTitle(title ?: "No title")
        .setContentText(body ?: "No body")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()

    manager.notify(System.currentTimeMillis().toInt(), notification)
}