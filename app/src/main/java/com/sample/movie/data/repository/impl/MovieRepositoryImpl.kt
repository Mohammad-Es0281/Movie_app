package com.sample.movie.data.repository.impl

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sample.movie.app.exception.ServerConnectionException
import com.sample.movie.app.extension.requireInternet
import com.sample.movie.data.local.database.MovieDatabase
import com.sample.movie.data.remote.api.MovieApi
import com.sample.movie.data.remote.mediator.MovieItemRemoteMediator
import com.sample.movie.data.repository.MovieRepository
import com.sample.movie.mapper.asUiModel
import com.sample.movie.presentation.model.MovieDetailModel
import com.sample.movie.presentation.model.MovieItemModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val movieDatabase: MovieDatabase,
    private val movieApi: MovieApi,
    @ApplicationContext private val context: Context
) : MovieRepository {

    override suspend fun getMovies(): Flow<PagingData<MovieItemModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieApi.PAGE_SIZE,
                initialLoadSize = MovieApi.PAGE_SIZE * 2,
                maxSize = MovieApi.PAGE_SIZE * 10,
                prefetchDistance = MovieApi.PAGE_SIZE / 4
            ),
            remoteMediator = MovieItemRemoteMediator(
                database = movieDatabase,
                api = movieApi,
                context = context
            ),
            pagingSourceFactory = {
                movieDatabase.movieItemDao.getMovies()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.asUiModel() }
        }
    }

    override suspend fun getMovie(movieId: Long): MovieDetailModel {
        requireInternet(context)
        val apiResponse = movieApi.getMovieDetail(movieId)

        if (!apiResponse.isSuccessful)
            throw ServerConnectionException(apiResponse.errorBody()?.string())

        val responseBody = apiResponse.body()!!

        return responseBody.asUiModel()
    }
}