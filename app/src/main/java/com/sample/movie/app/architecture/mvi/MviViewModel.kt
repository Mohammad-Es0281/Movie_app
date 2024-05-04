package com.sample.movie.app.architecture.mvi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.movie.app.architecture.mvvm.MvvmViewModel
import com.sample.movie.app.exception.asUiException

abstract class MviViewModel<STATE : UiState<*>, EVENT> : MvvmViewModel() {

    var uiState by mutableStateOf<UiState<*>>(UiState.Loading)
        protected set

    abstract fun onTriggerEvent(eventType: EVENT)

    override fun handleError(throwable: Throwable) {
        uiState = UiState.Error(throwable.asUiException())
    }
}