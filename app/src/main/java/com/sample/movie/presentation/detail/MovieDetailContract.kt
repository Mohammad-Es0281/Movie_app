package com.sample.movie.presentation.detail

import com.sample.movie.presentation.model.MovieDetailModel

data class MovieDetailUiState(
    val movie: MovieDetailModel
)

sealed class MovieDetailEvent {
    data class GetInformation(val movieId: Long): MovieDetailEvent()
}