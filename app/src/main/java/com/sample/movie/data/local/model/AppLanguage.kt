package com.sample.movie.data.local.model

sealed class AppLanguage(val code: String) {
    data object English : AppLanguage(code = "en")
    data object Persian : AppLanguage(code = "fa")
}