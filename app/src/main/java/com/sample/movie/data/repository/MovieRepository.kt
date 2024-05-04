package com.sample.movie.data.repository

import androidx.paging.PagingData
import com.sample.movie.presentation.model.MovieDetailModel
import com.sample.movie.presentation.model.MovieItemModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<PagingData<MovieItemModel>>

    suspend fun getMovie(movieId: Long): MovieDetailModel
}
