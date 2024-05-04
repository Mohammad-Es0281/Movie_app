package com.sample.movie.presentation.model

data class MovieItemModel(
    val id: Long,
    val title: String,
    val imageUrl: String?,
    val order: Long,
)