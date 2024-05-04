package com.sample.movie.app.init

import com.sample.movie.BuildConfig
import com.sample.movie.app.MovieApp
import timber.log.Timber

class TimberInitializer : AppInitializer {
    override fun init(application: MovieApp) {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}