package com.sample.movie.data.local.database.di

import android.content.Context
import androidx.room.Room
import com.sample.movie.data.local.database.MovieDatabase
import com.sample.movie.data.local.database.MovieItemDao
import com.sample.movie.data.local.database.MovieItemRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.util.concurrent.Executors


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun createDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        MovieDatabase.DB_NAME
    )
        .setQueryCallback(
            queryCallback = { sqlQuery, bindArgs -> Timber.d("SQL Query: $sqlQuery SQL Args: $bindArgs") },
            executor = Executors.newSingleThreadExecutor()
        ).build()

    @Provides
    fun provideMovieItemDao(appDatabase: MovieDatabase): MovieItemDao {
        return appDatabase.movieItemDao
    }

    @Provides
    fun provideMovieItemRemoteKeyDao(appDatabase: MovieDatabase): MovieItemRemoteKeyDao {
        return appDatabase.movieItemRemoteKeyDao
    }
}