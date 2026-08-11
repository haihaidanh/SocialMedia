package com.example.socialmedia1903.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.socialmedia1903.data.local.enitity.DbTracking
import com.example.socialmedia1903.data.local.enitity.PostEntity
import com.example.socialmedia1903.data.local.enitity.RemoteKeys
import com.example.socialmedia1903.data.local.room.AppDatabase
import com.example.socialmedia1903.data.mapper.HaiMapper.toEntity
import com.example.socialmedia1903.data.mapper.HaiMapper.toPostList
import com.example.socialmedia1903.data.remote.AppService
import com.example.socialmedia1903.domain.model.Post
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//class PostPagingSource(
//    private val apiService: AppService
//) : PagingSource<Int, Post>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
//        val page = params.key ?: 1
//
//        return try {
//            val response = apiService.getPosts(page)
//            val posts = response.posts.toPostList()
//
//            val nextKey = if (posts.size < 2) null else page + 1
//
//            LoadResult.Page(
//                data = posts,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = nextKey
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
//        return state.anchorPosition?.let { pos ->
//            state.closestPageToPosition(pos)?.nextKey?.minus(1)
//                ?: state.closestPageToPosition(pos)?.prevKey?.plus(1)
//        }
//    }
//}



@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator (
    private val appDatabase: AppDatabase,
    private val appService: AppService
) : RemoteMediator<Int, PostEntity>() {

    private val postDao = appDatabase.postDao()

    override suspend fun initialize(): InitializeAction {
        val timeLimit = TimeUnit.HOURS.toMillis(1)

        return try {
            val dbTracking = appDatabase.dbTrackingDao().getDbTacking()
            val isCacheValid = dbTracking != null &&
                    (System.currentTimeMillis() - dbTracking.lastUpdate < timeLimit)

            if (isCacheValid) {
                InitializeAction.SKIP_INITIAL_REFRESH
            } else {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            }
        } catch (e: Exception) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }
            val response = appService.getPosts(page)
            val posts = response.posts.toPostList()
            val pageSize = state.config.pageSize
            val endOfPaginationReached = posts.size < pageSize

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDao.clearAll()
                    appDatabase.remoteKeyDao().deleteAll()
                }

                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = posts.map { post ->
                    RemoteKeys(id = post.id, prevKey = prevKey, nextKey = nextKey)
                }

                postDao.insertAll(
                    posts.mapIndexed { index, post ->
                        post.toEntity(
                            page = page,
                            order = index
                        )
                    }
                )
                appDatabase.remoteKeyDao().insert(keys)
                appDatabase.dbTrackingDao().insert(
                    DbTracking(0, System.currentTimeMillis())
                )
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PostEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { post ->
                appDatabase.remoteKeyDao().getRemoteKey(post.id)
            }
    }
}