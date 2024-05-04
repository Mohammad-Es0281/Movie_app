package com.sample.movie.app.architecture.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.movie.app.extension.classTag
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class MvvmViewModel : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.tag(SAFE_LAUNCH_EXCEPTION).e(exception)
        handleError(exception)
    }

    init { Timber.tag(this.classTag).d("Initialized") }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(this.classTag).d("Cleared")
    }

    open fun handleError(throwable: Throwable) {}

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(handler, block = block)

    companion object {
        private const val SAFE_LAUNCH_EXCEPTION = "ViewModel-ExceptionHandler"
    }
}