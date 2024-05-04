package com.sample.movie.mapper

import com.sample.movie.data.local.model.MovieItemEntity
import com.sample.movie.data.remote.model.MovieItemDto
import com.sample.movie.presentation.model.MovieItemModel

fun MovieItemDto.asEntity(): MovieItemEntity {
    return MovieItemEntity(
        serverId = this.id!!,
        title = this.title!!,
        imageUrl = MovieItemDto.POSTER_SIZE_PREFIX + this.posterPath,
        popularity = this.popularity!!,
    )
}

fun List<MovieItemDto>.asEntity() = this.map { it.asEntity() }

fun MovieItemEntity.asUiModel(): MovieItemModel {
    return MovieItemModel(
        id = this.serverId,
        title = this.title,
        imageUrl = this.imageUrl,
        order = this.localId
    )
}

fun List<MovieItemEntity>.asUiModel() = this.map { it.asUiModel() }
