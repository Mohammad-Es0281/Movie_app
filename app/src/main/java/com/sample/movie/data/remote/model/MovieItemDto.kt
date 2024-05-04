package com.sample.movie.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieItemDto(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "popularity")
    val popularity: Double?,
) {
    companion object {
        const val POSTER_SIZE_PREFIX = "w300"
    }
}