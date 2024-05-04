package com.sample.movie.app.init

import com.sample.movie.app.MovieApp

class AppInitializerImpl(private vararg val initializers: AppInitializer) : AppInitializer {
    override fun init(application: MovieApp) {
        initializers.forEach { it.init(application) }
    }
}