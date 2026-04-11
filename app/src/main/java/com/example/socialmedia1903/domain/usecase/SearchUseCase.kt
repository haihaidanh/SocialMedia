package com.example.socialmedia1903.domain.usecase

import com.example.socialmedia1903.data.dto.response.SearchResultResponse
import com.example.socialmedia1903.data.source.RemoteDataSource
import com.example.socialmedia1903.domain.model.SearchResult
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(text: String): SearchResult {
        val result = remoteDataSource.search(text)
        return remoteDataSource.search(text)
    }

}