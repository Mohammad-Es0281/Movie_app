package com.sample.movie.data.local.model

sealed interface DarkMode {
        data object On : DarkMode
        data object Off : DarkMode
        data object System : DarkMode
    }