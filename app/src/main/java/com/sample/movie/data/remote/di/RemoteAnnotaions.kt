package com.sample.movie.data.remote.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseStorageUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessToken
