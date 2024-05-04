package com.sample.movie.data.remote.di

import com.sample.movie.BuildConfig
import com.sample.movie.data.remote.api.MovieApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    @BaseStorageUrl
    fun provideBaseStorageUrl(): String {
        return BuildConfig.BASE_STORAGE_URL
    }

    @Provides
    @Singleton
    @AccessToken
    fun provideAccessToken(): String {
        return BuildConfig.ACCESS_TOKEN
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideHttpRequestInterceptor(@AccessToken accessToken: String): AccessTokenInterceptor {
        return AccessTokenInterceptor(accessToken)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(accessTokenInterceptor)
            followSslRedirects(true)
            followRedirects(true)
            retryOnConnectionFailure(true)
            readTimeout(DEFAULT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            connectTimeout(DEFAULT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            callTimeout(DEFAULT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
        }.build()
    }

    private inline fun <reified T> createRetrofitWithMoshi(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        baseUrl: String
    ): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(T::class.java)
    }

    @Provides
    fun provideMovieApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) = createRetrofitWithMoshi<MovieApi>(
        okHttpClient = okHttpClient,
        moshi = moshi,
        baseUrl = baseUrl
    )

    companion object {
         const val DEFAULT_TIME_OUT_SECONDS = 5L
    }
}