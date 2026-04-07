package com.example.socialmedia1903.domain.usecase

import android.util.Log
import com.example.socialmedia1903.data.dto.response.SearchResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(text: String): SearchResponse{
        val result = remoteDataSource.search(text)
        return remoteDataSource.search(text)
    }

}