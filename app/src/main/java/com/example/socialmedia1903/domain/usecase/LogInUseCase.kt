package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.LogInResponse
import com.example.socialmedia1903.data.source.LocalDataSource
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
){
    suspend operator fun invoke(name: String, password: String): Boolean{
        val response =  remoteDataSource.logIn(name, password)
        if(response.errCode == 1){
            return false
        }
        localDataSource.saveTokens(response)
        return true
    }
}