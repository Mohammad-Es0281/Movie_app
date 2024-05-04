package com.sample.movie.data.remote.mediator

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sample.movie.app.exception.ServerConnectionException
import com.sample.movie.app.extension.requireInternet
import com.sample.movie.data.local.database.MovieDatabase
import com.sample.movie.data.local.model.MovieItemEntity
import com.sample.movie.data.local.model.MovieItemRemoteKeyEntity
import com.sample.movie.data.remote.api.MovieApi
import com.sample.movie.mapper.asEntity

@OptIn(ExperimentalPagingApi::class)
class MovieItemRemoteMediator(
    private val database: MovieDatabase,
    private val api: MovieApi,
    private val context: Context,
): RemoteMediator<Int, MovieItemEntity>() {

    private val movieDao = database.movieItemDao
    private val remoteKeyDao = database.movieItemRemoteKeyDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieItemEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> remoteKeyDao.getRemoteKey()?.nextKey
            } ?: 1

            requireInternet(context)
            val apiResponse = api.getPopularMovies(page = loadKey)

            if (!apiResponse.isSuccessful)
                return MediatorResult.Error(ServerConnectionException(apiResponse.errorBody()?.string()))

            val responseBody = apiResponse.body()!!

            database.withTransaction {
                if(loadType == LoadType.REFRESH)
                    movieDao.clearAll()

                val entities = responseBody.movieItems?.asEntity()!!

                movieDao.upsertAll(entities)

                remoteKeyDao.clear()
                remoteKeyDao.upsert(MovieItemRemoteKeyEntity(responseBody.page!! + 1))
            }

            MediatorResult.Success(endOfPaginationReached = responseBody.page!! == responseBody.totalPages!!)
        } catch(e: Exception) {
            MediatorResult.Error(e)
        }
    }
}