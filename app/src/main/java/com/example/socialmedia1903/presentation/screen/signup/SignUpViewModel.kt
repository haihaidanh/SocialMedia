package com.example.socialmedia1903.presentation.screen.signup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.PostResponse
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

    fun signUp(
        image: Uri,
        name: String,
        password: String,
        gender: Int,
        context: Context
    ){
        viewModelScope.launch {
            val result = signUpUseCase(image, name, password, gender, context)
            _isSignUpSuccess.value = if(result.errCode == 0 ) true else false
        }
    }
}