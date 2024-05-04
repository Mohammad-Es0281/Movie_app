package com.sample.movie.app.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sample.movie.app.architecture.mvi.UiState
import com.sample.movie.app.exception.UiException
import com.sample.movie.presentation.util.ScreenAnimationSpecs

@Composable
fun <T> BaseScreen(
    modifier: Modifier = Modifier,
    state: UiState<T>,
    content: @Composable (UiState.Success<T>) -> Unit,
    loading: @Composable (UiState.Loading) -> Unit = {
        LoadingContent()
    },
    onRetry: (() -> Unit)? = null,
    error: @Composable (UiState.Error) -> Unit = { errorState ->
        ErrorContent(
            exception = errorState.exception,
            onRetry = onRetry
        )
    },
    empty: @Composable (UiState.Empty) -> Unit = { _ ->
        EmptyUi(modifier = Modifier.fillMaxSize())
    },
) {
    AnimatedContent(
        modifier = modifier,
        targetState = state,
        transitionSpec = {
            ScreenAnimationSpecs.fadeIn togetherWith ScreenAnimationSpecs.fadeOut
        },
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            is UiState.Success -> content(targetState)

            is UiState.Error -> error(targetState)

            is UiState.Empty -> empty(targetState)

            is UiState.Loading -> loading(targetState)
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LoadingUi(modifier = Modifier.fillMaxSize(0.6F))
    }
}

@Composable
private fun ErrorContent(
    exception: UiException,
    onRetry: (() -> Unit)? = null,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ErrorUi(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6F),
            exception = exception,
            onRetry = onRetry
        )
    }
}