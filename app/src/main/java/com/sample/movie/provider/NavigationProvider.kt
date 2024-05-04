package com.sample.movie.provider

import androidx.navigation.NavController

interface NavigationProvider {
    val nav: NavController

    fun navigateUp()
    fun navigateToMovieDetail(movieId: Long)
}