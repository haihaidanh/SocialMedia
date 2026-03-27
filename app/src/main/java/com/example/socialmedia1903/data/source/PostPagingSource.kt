package com.example.socialmedia1903.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.domain.model.Post

class PostPagingSource(
    private val apiService: AppService // Retrofit service
) : PagingSource<Int, PostResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostResponse> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getPosts(page) // gọi API
            val posts = response.posts // giả sử API trả { data: List<Post>, total: Int }

            LoadResult.Page(
                data = posts,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (posts.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostResponse>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.nextKey?.minus(1)
                ?: state.closestPageToPosition(pos)?.prevKey?.plus(1)
        }
    }
}