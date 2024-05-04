package com.sample.movie.app.architecture.mvi

import com.sample.movie.app.exception.UiException

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object Empty : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val exception: UiException) : UiState<Nothing>
}