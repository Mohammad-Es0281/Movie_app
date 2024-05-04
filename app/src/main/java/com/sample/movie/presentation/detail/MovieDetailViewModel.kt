package com.sample.movie.presentation.detail

import com.sample.movie.app.architecture.mvi.MviViewModel
import com.sample.movie.app.architecture.mvi.UiState
import com.sample.movie.data.repository.MovieRepository
import com.sample.movie.presentation.main.di.MovieIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    @MovieIdArg private val movieId: Long,
) : MviViewModel<UiState<MovieDetailUiState>, MovieDetailEvent>() {

    init {
        onTriggerEvent(MovieDetailEvent.GetInformation(movieId))
    }

    override fun onTriggerEvent(eventType: MovieDetailEvent) {
        when(eventType) {
            is MovieDetailEvent.GetInformation -> onGetInformation(eventType)
        }
    }

    private fun onGetInformation(eventType: MovieDetailEvent.GetInformation) {
        safeLaunch {
            uiState = UiState.Loading

            val movieDetail = repository.getMovie(eventType.movieId)

            uiState = UiState.Success(MovieDetailUiState(movieDetail))
        }
    }
}