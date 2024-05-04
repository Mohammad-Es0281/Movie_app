package com.sample.movie.presentation.main.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MovieIdArg

@Module
@InstallIn(ViewModelComponent::class)
object ContestIdModule {

    @Provides
    @ViewModelScoped
    @MovieIdArg
    fun provideViewModelWithMovieId(
        savedStateHandle: SavedStateHandle,
    ): Long = savedStateHandle.get<Long>("movieId")!!
}