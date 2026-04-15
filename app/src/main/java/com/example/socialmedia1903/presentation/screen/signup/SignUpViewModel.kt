package com.example.socialmedia1903.presentation.screen.signup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.model.ErrSignIn
import com.example.socialmedia1903.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel(){
    private val _isSignUpSuccess = MutableStateFlow(false)
    val isSignUpSuccess: StateFlow<Boolean> = _isSignUpSuccess

    private val _error = MutableStateFlow(ErrSignIn())
    val error: StateFlow<ErrSignIn> = _error

    fun signUp(
        image: Uri,
        username: String,
        name: String,
        password: String,
        confirmPassword: String,
        gender: Int,
        context: Context
    ){
        viewModelScope.launch {

            if(username.isEmpty()){
                _error.value = ErrSignIn(errUserName = "Username cannot be empty")
            }else if (name.length < 3){
                _error.value = ErrSignIn(errName = "Name must be at least 3 characters")
            }else if (password.length < 6){
                _error.value = ErrSignIn(errPassword = "Password must be at least 6 characters")
            }else if (confirmPassword != password){
                _error.value = ErrSignIn(errConfirmPassword = "Confirm password does not match")
            }else{
                _error.value = ErrSignIn()
                val result = signUpUseCase(image, name, username, password, gender, context)
                _isSignUpSuccess.value = if(result.errCode == 0 ) true else false
            }
        }
    }
}