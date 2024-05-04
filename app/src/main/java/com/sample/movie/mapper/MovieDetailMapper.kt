package com.sample.movie.mapper

import com.sample.movie.data.remote.model.MovieDetailDto
import com.sample.movie.presentation.model.MovieLengthModel
import com.sample.movie.presentation.model.MovieDetailModel
import com.sample.movie.presentation.model.MovieGenreModel

fun MovieDetailDto.asUiModel(): MovieDetailModel {
    return MovieDetailModel(
        id = this.id!!,
        name = this.title!!,
        image = MovieDetailDto.POSTER_SIZE_PREFIX + this.posterPath!!,
        voteAverage = String.format("%.2f", this.voteAverage).toFloat(),
        length = MovieLengthModel(hour = this.runtime!!.div(60), minute = this.runtime.mod(60)),
        language = this.spokenLanguages!!.joinToString { it!!.iso6391!! }.uppercase(),
        genres = this.movieGenres?.mapNotNull { MovieGenreModel(it!!.id!!, it.name!!) }?: emptyList(),
        description = this.overview!!
    )
}