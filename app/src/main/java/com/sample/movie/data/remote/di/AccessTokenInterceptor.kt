package com.sample.movie.data.remote.di

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    @AccessToken private val accessToken: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $accessToken"
            )
            .url(originalRequest.url)
            .build()

        return chain.proceed(request)
    }
}