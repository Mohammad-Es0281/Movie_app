package com.sample.movie.app.init

import coil.Coil
import com.sample.movie.app.MovieApp
import com.sample.movie.app.init.coil.CoilFactory

class CoilInitializer(private val baseStorageUrl: String) : AppInitializer {
    override fun init(application: MovieApp) {
        Coil.setImageLoader(CoilFactory(context = application, basePath = baseStorageUrl))
    }
}