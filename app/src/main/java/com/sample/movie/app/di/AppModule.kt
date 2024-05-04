package com.sample.movie.app.di

import com.sample.movie.app.MovieApp
import com.sample.movie.app.init.AppInitializer
import com.sample.movie.app.init.AppInitializerImpl
import com.sample.movie.app.init.CoilInitializer
import com.sample.movie.app.init.TimberInitializer
import com.sample.movie.data.remote.di.BaseStorageUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppInitializer(
        timberInitializer: TimberInitializer,
        coilInitializer: CoilInitializer,
    ): AppInitializer {
        return AppInitializerImpl(timberInitializer, coilInitializer)
    }

    @Provides
    @Singleton
    fun provideApplication(): MovieApp {
        return MovieApp()
    }

    @Provides
    @Singleton
    fun provideTimberInitializer() = TimberInitializer()

    @Provides
    @Singleton
    fun provideCoilInitializer(@BaseStorageUrl baseStorageUrl: String) =
        CoilInitializer(baseStorageUrl)
}