package com.example.socialmedia1903.data.source

import com.example.socialmedia1903.data.dto.response.GoogleSignInResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun signInWithGoogle(idToken: String): Result<GoogleSignInResponse> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)

            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user!!

            Result.success(
                GoogleSignInResponse(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName,
                    email = firebaseUser.email,
                    avatar = firebaseUser.photoUrl?.toString()
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}