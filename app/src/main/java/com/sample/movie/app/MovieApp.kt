package com.sample.movie.app

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import com.sample.movie.app.init.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MovieApp: Application() {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var initializer: AppInitializer

    override fun onCreate() {
        super.onCreate()

        initializer.init(this)
        registerActivityLifecycleCallbacks(ActivityLifecycleCallback())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}