package com.sample.movie.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailDto(
    @Json(name = "genres")
    val movieGenres: List<MovieGenreDto?>?,
    @Json(name = "id")
    val id: Long?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto?>?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
) {
    companion object {
        const val POSTER_SIZE_PREFIX = "original"
    }
}