package com.sample.movie.data.local.model

sealed interface AppTheme {
    data object Default : AppTheme
    data object System : AppTheme
}
