package com.sample.movie.data.local.database

import androidx.room.Dao
import androidx.room.Query
import com.sample.movie.data.local.model.MovieItemRemoteKeyEntity

@Dao
interface MovieItemRemoteKeyDao : BaseDao<MovieItemRemoteKeyEntity> {
    @Query("SELECT * FROM movie_item_remote_keys")
    suspend fun getRemoteKey(): MovieItemRemoteKeyEntity?

    @Query("DELETE FROM movie_item_remote_keys")
    suspend fun clear()
}