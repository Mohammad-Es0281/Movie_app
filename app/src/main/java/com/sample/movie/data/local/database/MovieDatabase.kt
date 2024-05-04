package com.sample.movie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.movie.data.local.model.MovieItemEntity
import com.sample.movie.data.local.model.MovieItemRemoteKeyEntity

@Database(
    entities = [MovieItemEntity::class, MovieItemRemoteKeyEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieItemDao: MovieItemDao
    abstract val movieItemRemoteKeyDao: MovieItemRemoteKeyDao

    companion object {
        const val DB_NAME = "Movie.db"
    }
}
