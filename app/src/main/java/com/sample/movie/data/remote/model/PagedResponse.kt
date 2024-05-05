package com.sample.movie.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagedResponse<T>(
    @Json(name = "page")
    val page: Int?,
    @Json(name = "results")
    val movieItems: List<T>?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "total_results")
    val totalResults: Int?
)