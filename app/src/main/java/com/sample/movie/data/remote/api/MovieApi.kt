package com.sample.movie.data.remote.api

import com.sample.movie.data.remote.model.MovieDetailDto
import com.sample.movie.data.remote.model.PagedResponse
import com.sample.movie.data.remote.model.MovieItemDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<PagedResponse<MovieItemDto>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Long
    ): Response<MovieDetailDto>

    companion object {
        const val PAGE_SIZE = 20
    }
}