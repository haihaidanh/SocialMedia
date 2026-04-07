package com.example.socialmedia1903.data.firebase

import android.util.Log
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.presentation.screen.login.LoginScreen
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val appService: AppService
) {
    fun sendTokenToBE(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener{ token ->
            if(!token.isSuccessful){
                Log.d("hai", "token failed")
                return@addOnCompleteListener
            }
            appService.sendToken(token.result)
        }
    }
}