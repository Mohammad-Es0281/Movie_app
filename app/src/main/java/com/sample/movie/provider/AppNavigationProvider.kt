package com.sample.movie.provider

import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.sample.movie.destinations.MovieDetailRouteDestination

class AppNavigationProvider(
    private val navController: NavController,
) : NavigationProvider {

    override val nav: NavController
        get() = navController

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigateToMovieDetail(movieId: Long) {
        navController.navigate(MovieDetailRouteDestination(movieId))
    }
}