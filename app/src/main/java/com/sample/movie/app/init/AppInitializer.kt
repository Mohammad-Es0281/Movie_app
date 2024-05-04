package com.sample.movie.app.init

import com.sample.movie.app.MovieApp

interface AppInitializer {
    fun init(application: MovieApp)
}