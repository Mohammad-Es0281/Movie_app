package com.sample.movie.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenreDto(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
)