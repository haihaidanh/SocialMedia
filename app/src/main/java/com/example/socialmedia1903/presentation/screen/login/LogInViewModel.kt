package com.example.socialmedia1903.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedia1903.data.dto.response.LogInResponse
import com.example.socialmedia1903.data.model.ErrLogIn
import com.example.socialmedia1903.data.source.LocalDataSource
import com.example.socialmedia1903.domain.usecase.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val localDataSource: LocalDataSource
): ViewModel() {
    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow(ErrLogIn())
    val error: StateFlow<ErrLogIn> = _error


    fun logIn(name: String, password: String){
        viewModelScope.launch {
            if(name.length < 3){
                _error.value = ErrLogIn(errName = "Username must be at least 3 characters")
            }else if (password.length < 6){
                _error.value = ErrLogIn(errPassword = "Password must be at least 6 characters")
            }else{
                logInUseCase(name, password)
                _loading.value = false
            }

        }
    }

    fun isLoggedIn(): Boolean {
        return localDataSource.getAccessToken() != null
    }

}