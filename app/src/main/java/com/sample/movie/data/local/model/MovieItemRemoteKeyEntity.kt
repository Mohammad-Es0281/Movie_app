package com.sample.movie.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_item_remote_keys")
data class MovieItemRemoteKeyEntity(
    @PrimaryKey
    val nextKey: Int?
)