package com.sample.movie.presentation.model

data class MovieDetailModel(
    val id: Long,
    val name: String,
    val image: String,
    val voteAverage: Float,
    val length: MovieLengthModel,
    val language: String,
    val genres: List<MovieGenreModel>,
    val description: String
)