package com.sample.movie.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.sample.movie.data.local.model.MovieItemEntity

@Dao
interface MovieItemDao : BaseDao<MovieItemEntity> {
    @Query("SELECT * FROM tb_movie_item ORDER BY local_id ASC")
    fun getMovies(): PagingSource<Int, MovieItemEntity>

    @Query("DELETE FROM tb_movie_item")
    suspend fun clearAll()
}