package com.example.socialmedia1903.data.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.example.socialmedia1903.presentation.screen.notification.showNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.data["title"]
        val body = message.data["body"]
        val imageUrl = message.data["imageUrl"]

        Log.d("hai", message.data.toString())

        showNotification(applicationContext, title, body, imageUrl)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // gửi token lên backend
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Default Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}