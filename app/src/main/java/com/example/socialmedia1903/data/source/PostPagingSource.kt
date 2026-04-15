package com.example.socialmedia1903.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.mapper.HaiMapper.toPostList
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.domain.model.Post

class PostPagingSource(
    private val apiService: AppService
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: 1

        return try {
            val response = apiService.getPosts(page)
            val posts = response.posts.toPostList()

            val nextKey = if (posts.size < 2) null else page + 1

            LoadResult.Page(
                data = posts,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.nextKey?.minus(1)
                ?: state.closestPageToPosition(pos)?.prevKey?.plus(1)
        }
    }
}