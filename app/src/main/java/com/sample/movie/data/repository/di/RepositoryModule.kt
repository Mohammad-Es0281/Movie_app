package com.sample.movie.data.repository.di

import com.sample.movie.data.repository.AppSettingsRepository
import com.sample.movie.data.repository.MovieRepository
import com.sample.movie.data.repository.impl.AppSettingsRepositoryImpl
import com.sample.movie.data.repository.impl.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsAppSettingsRepository(repo: AppSettingsRepositoryImpl): AppSettingsRepository

    @Singleton
    @Binds
    fun bindsMovieRepository(repo: MovieRepositoryImpl): MovieRepository
}