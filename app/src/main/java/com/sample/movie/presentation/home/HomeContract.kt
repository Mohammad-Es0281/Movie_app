package com.sample.movie.presentation.home

import androidx.paging.PagingData
import com.sample.movie.presentation.model.MovieItemModel
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val movieItems: Flow<PagingData<MovieItemModel>>
)

sealed class HomeEvent {
    data object GetInformation: HomeEvent()
}