package com.sample.movie.presentation.main.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sample.movie.app.architecture.mvi.MviViewModel
import com.sample.movie.app.architecture.mvi.UiState
import com.sample.movie.data.repository.MovieRepository
import com.sample.movie.presentation.home.HomeEvent
import com.sample.movie.presentation.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : MviViewModel<UiState<HomeUiState>, HomeEvent>() {

    init {
        onTriggerEvent(HomeEvent.GetInformation)
    }

    override fun onTriggerEvent(eventType: HomeEvent) {
        when(eventType) {
            is HomeEvent.GetInformation -> onGetInformation(eventType)
        }
    }

    private fun onGetInformation(eventType: HomeEvent.GetInformation) {
        safeLaunch {
            uiState = UiState.Loading

            val pagedMovies = repository.getMovies().cachedIn(viewModelScope)

            uiState = UiState.Success(HomeUiState(pagedMovies))
        }
    }
}